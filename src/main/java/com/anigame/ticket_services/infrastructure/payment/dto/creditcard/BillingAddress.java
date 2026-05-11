package com.anigame.ticket_services.infrastructure.payment.dto.creditcard;

public record BillingAddress(
        String street,
        String number,
        String neighborhood,
        String zipcode,
        String city,
        String state
) {
}
