package com.anigame.ticket_services.data.mapper;

import com.anigame.ticket_services.domain.new_impl.Payment;
import com.anigame.ticket_services.z_domain.PaymentEntity;
import com.anigame.ticket_services.infrastructure.persistence.payment.PaymentMethodEnumEntity;
import com.anigame.ticket_services.infrastructure.persistence.payment.PaymentStatusEnumEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PaymentMapper {

    public PaymentEntity toEntityWithoutId (Payment payment) {
        return PaymentEntity.builder()
                .orderId(payment.getOrderId())
                .provider(payment.getProvider())
                .method(PaymentMethodEnumEntity.valueOf(payment.getMethod().name()))
                .providerChargeId(payment.getProviderChargeId())
                .providerTxId(payment.getProviderTxId())
                .providerStatus(payment.getProviderStatus())
                .amount(BigDecimal.valueOf(payment.getAmount()).scaleByPowerOfTen(2).intValueExact())
                .status(PaymentStatusEnumEntity.valueOf(payment.getStatus().name()))
                .idempotencyKey(payment.getIdempotencyKey())
                .failureReason(payment.getFailureReason())
                .rawResponse(payment.getRawResponse())
                .expiresAt(payment.getExpiresAt())
                .confirmedAt(payment.getConfirmedAt())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .build();
    }
}
