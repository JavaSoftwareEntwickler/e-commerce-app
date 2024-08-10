package com.shoponline.ecommerce.order;

import com.shoponline.ecommerce.customer.CustomerClient;
import com.shoponline.ecommerce.exception.BusinessException;
import com.shoponline.ecommerce.orderline.OrderLine;
import com.shoponline.ecommerce.orderline.OrderLineRequest;
import com.shoponline.ecommerce.orderline.OrderLineService;
import com.shoponline.ecommerce.product.ProductClient;
import com.shoponline.ecommerce.product.PurchaseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    public Integer createOrder(OrderRequest request) {

        //check customer --> OpenFeign
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(()-> new BusinessException("Cannot create order:: No Customer exists with the provided ID::" + request.customerId()));

        //purchase the products --> product-service (RestTemplate e non OpenFeign)
        this.productClient.purchaseProducts(request.products());

        //persiste order
        var order = this.repository.save(mapper.toOrder(request));

        // persiste order lines
        for (PurchaseRequest purchaseRequest: request.products()){

            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        //TODO start payment process

        //TODO send order confermation to customer --> notification-service (send message to kafka broker)

        return order.getId();
    }
}
