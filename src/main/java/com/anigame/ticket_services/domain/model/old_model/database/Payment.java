package com.anigame.ticket_services.domain.model.old_model.database;

import com.anigame.ticket_services.domain.model.old_model.database.enumerate.PaymentMethodEnum;
import com.anigame.ticket_services.domain.model.old_model.database.enumerate.PaymentStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class Payment {
    private UUID id;
    private UUID orderId;
    private String provider;
    private PaymentMethodEnum method;
    private String providerChargeId;
    private String providerTxId;
    private String providerStatus;
    private Integer amount;
    private PaymentStatusEnum status;
    private String idempotencyKey;
    private String failureReason;
    private String rawResponse;
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
