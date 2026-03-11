package com.anigame.ticket_services.data.dto;

import com.anigame.ticket_services.domain.new_impl.PaymentMethod;
import com.anigame.ticket_services.domain.new_impl.PaymentStatus;

public sealed interface PaymentResponseDTO permits PixPaymentResponse, CreditCardPaymentResponse {
    PaymentMethod method();
    PaymentStatus status();
}
