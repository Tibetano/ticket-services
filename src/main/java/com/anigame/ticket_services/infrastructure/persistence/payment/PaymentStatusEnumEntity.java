package com.anigame.ticket_services.infrastructure.persistence.payment;

public enum PaymentStatusEnumEntity {
    PENDING,
    WAITING_PAYMENT,
    CONFIRMED,
    FAILED,
    CANCELED,
    REFUNDED
}
