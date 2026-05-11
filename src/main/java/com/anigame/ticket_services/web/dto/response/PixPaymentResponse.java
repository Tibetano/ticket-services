package com.anigame.ticket_services.web.dto.response;

import com.anigame.ticket_services.domain.enums.PaymentMethodEnumEntity;
import com.anigame.ticket_services.domain.enums.PaymentStatusEnumEntity;
import lombok.Builder;

@Builder
public record PixPaymentResponse(
        PaymentMethodEnumEntity method,
        PaymentStatusEnumEntity status,
        PixPaymentDetailsResponse details
) implements PaymentResponseDTO {

    @Override
    public PaymentMethodEnumEntity method() {

        return PaymentMethodEnumEntity.PIX;
    }
}
