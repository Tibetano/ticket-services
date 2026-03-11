package com.anigame.ticket_services.z_domain;

import com.anigame.ticket_services.infrastructure.persistence.payment.PaymentMethodEnumEntity;
import com.anigame.ticket_services.infrastructure.persistence.payment.PaymentStatusEnumEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "payment",
        schema = "ticket_services"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid DEFAULT gen_random_uuid()", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "order_id", nullable = false)
    private UUID orderId;
    @Column(nullable = false)
    private String provider;
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false)
    private PaymentMethodEnumEntity method;
    @Column(name = "provider_charge_id")
    private String providerChargeId;
    @Column(name = "provider_txid")
    private String providerTxId;
    @Column(name = "provider_status")
    private String providerStatus;
    @Column(nullable = false)
    private Integer amount;
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false)
    private PaymentStatusEnumEntity status;
    @Column(name = "idempotency_key", unique = true)
    private String idempotencyKey;
    @Column(name = "failure_reason")
    private String failureReason;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "raw_response", columnDefinition = "jsonb")
    private String rawResponse;
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
