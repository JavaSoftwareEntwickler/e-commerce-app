package com.shoponline.ecommerce.order;

import com.shoponline.ecommerce.customer.CustomerClient;
import com.shoponline.ecommerce.exception.BusinessException;
import com.shoponline.ecommerce.kafka.OrderConfirmation;
import com.shoponline.ecommerce.kafka.OrderProducer;
import com.shoponline.ecommerce.orderline.OrderLineRequest;
import com.shoponline.ecommerce.orderline.OrderLineService;
import com.shoponline.ecommerce.payment.PaymentClient;
import com.shoponline.ecommerce.payment.PaymentRequest;
import com.shoponline.ecommerce.product.ProductClient;
import com.shoponline.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;
    public Integer createOrder(OrderRequest request) {

        //check customer --> OpenFeign
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(()-> new BusinessException("Cannot create order:: No Customer exists with the provided ID::" + request.customerId()));
        log.info("Customer found ::" + customer.firstName() + " "+customer.lastName());
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
        log.info("start payment process");
        //start payment process
        paymentClient.getOrderPayment(
                new PaymentRequest(
                        null,
                        request.amount(),
                        request.paymentMethod(),
                        order.getId(),
                        order.getReference(),
                        customer
                )
        );
        log.info("finish payment process..");
        log.info("start sending order confirmation....");
        //send order confirmation to customer --> notification-service (send message to kafka broker)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );
        log.info("sent order confirmation....");
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
