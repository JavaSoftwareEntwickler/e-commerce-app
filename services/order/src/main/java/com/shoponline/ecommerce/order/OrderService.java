package com.shoponline.ecommerce.order;

import com.shoponline.ecommerce.customer.CustomerClient;
import com.shoponline.ecommerce.exception.BusinessException;
import com.shoponline.ecommerce.kafka.OrderConfirmation;
import com.shoponline.ecommerce.kafka.OrderProducer;
import com.shoponline.ecommerce.orderline.OrderLineRequest;
import com.shoponline.ecommerce.orderline.OrderLineService;
import com.shoponline.ecommerce.product.ProductClient;
import com.shoponline.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    public Integer createOrder(OrderRequest request) {

        //check customer --> OpenFeign
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(()-> new BusinessException("Cannot create order:: No Customer exists with the provided ID::" + request.customerId()));

        //purchase the products --> product-service (RestTemplate e non OpenFeign)
        var purchasedProducts= this.productClient.purchaseProducts(request.products());

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

        //send order confermation to customer --> notification-service (send message to kafka broker)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );
        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::fromOrderToOrderResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return repository.findById(orderId)
                .map(mapper::fromOrderToOrderResponse)
                .orElseThrow(()-> new EntityNotFoundException("Order not found with the provided ID::" +orderId));
    }
}
