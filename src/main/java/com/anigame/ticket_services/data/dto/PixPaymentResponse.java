package com.anigame.ticket_services.data.dto;

import com.anigame.ticket_services.domain.new_impl.PaymentMethod;
import com.anigame.ticket_services.domain.new_impl.PaymentStatus;
import lombok.Builder;

@Builder
public record PixPaymentResponse(
        PaymentMethod method,
        PaymentStatus status,
        PixPaymentResponseDetails details
) implements PaymentResponseDTO {

    @Override
    public PaymentMethod method() {
        return PaymentMethod.PIX;
    }
}
