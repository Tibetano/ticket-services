package com.anigame.ticket_services.web.dto.response;

import com.anigame.ticket_services.domain.enums.PaymentMethodEnumEntity;
import lombok.Builder;

@Builder
public record PaymentMethodResponseDTO(
        PaymentMethodEnumEntity type,
        FeeDTO fee
) {
}
