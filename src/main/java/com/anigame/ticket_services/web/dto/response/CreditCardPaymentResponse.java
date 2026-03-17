package com.anigame.ticket_services.web.dto.response;

import com.anigame.ticket_services.domain.enums.PaymentMethodEnumEntity;
import com.anigame.ticket_services.domain.enums.PaymentStatusEnumEntity;
import lombok.Builder;

@Builder
public record CreditCardPaymentResponse(
        PaymentMethodEnumEntity method,
        PaymentStatusEnumEntity status,
        String details
) implements PaymentResponseDTO {

    @Override
    public PaymentMethodEnumEntity method() {
        return PaymentMethodEnumEntity.CREDIT_CARD;
    }
}
