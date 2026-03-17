package com.anigame.ticket_services.infrastructure.payment.dto.creditcard;

import com.anigame.ticket_services.infrastructure.payment.dto.*;

import java.util.List;

public record CreditCardPaymentRequest(
        Payment payment,
        List<Item> items,
        Metadata metadata
) {

    public static CreditCardPaymentRequest from(
            String customerName,
            String customerCPF,
            String customerPhone,
            String customerEmail,
            Integer installments,
            String paymentToken,
            List<OrderItem> orderItems
    ) {

        Customer customer = new Customer(
                customerName,
                customerCPF,
                customerPhone,
                customerEmail
        );

        CreditCard creditCard = new CreditCard(
                installments,
                paymentToken,
                customer
        );

        Payment payment = new Payment(creditCard);

        List<Item> items = orderItems.stream()
                .filter(i -> i.quantity() > 0)
                .map(i -> new Item(
                        i.type().name(),
                        i.quantity(),
                        i.unitPrice()
                ))
                .toList();

        Metadata metadata = new Metadata(
                "https://requestb.in/16rpx6y1",
                "id_0007"
        );

        return new CreditCardPaymentRequest(payment, items, metadata);
    }
}
