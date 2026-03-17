package com.anigame.ticket_services.web.dto.response;

import com.anigame.ticket_services.domain.enums.PaymentMethodEnumEntity;
import com.anigame.ticket_services.domain.enums.PaymentStatusEnumEntity;

public sealed interface PaymentResponseDTO permits PixPaymentResponse, CreditCardPaymentResponse {
    PaymentMethodEnumEntity method();
    PaymentStatusEnumEntity status();
}
