package com.anigame.ticket_services.infrastructure.payment.dto.creditcard;

import com.anigame.ticket_services.infrastructure.payment.dto.OrderItem;

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
            String notificationUrl,
            List<OrderItem> orderItems
    ) {

        Customer customer = new Customer(
                customerName,
                customerCPF,
                customerPhone,
                customerEmail
        );

        BillingAddress billingAddress = new BillingAddress(
                "Rua Exemplo",
                "123",
                "Centro",
                "39400000",
                "Janaúba",
                "MG"
        );

        CreditCard creditCard = new CreditCard(
                installments,
                paymentToken,
                billingAddress,
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
                notificationUrl,
                null//"id_0007"
        );

        return new CreditCardPaymentRequest(payment, items, metadata);
    }
}
