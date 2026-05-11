package com.anigame.ticket_services.domain;

import com.anigame.ticket_services.domain.enums.PaymentMethodEnumEntity;
import com.anigame.ticket_services.domain.enums.PaymentStatusEnumEntity;
import com.anigame.ticket_services.domain.order.OrderEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "payment",
        schema = "ticket_services"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid DEFAULT gen_random_uuid()", updatable = false, nullable = false)
    private UUID id;

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


    @OneToOne
    //@Column(name = "order_id", nullable = false)
    @JoinColumn(name = "order_id", unique = true)
    private OrderEntity orderEntity;


    public static PaymentEntity pix(OrderEntity order, String transactionId) {

        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        if (transactionId == null || transactionId.isBlank()) {
            throw new IllegalArgumentException("TransactionId cannot be null");
        }

        return PaymentEntity.builder()
                .orderEntity(order)
                .provider("Efí-Bank")
                .method(PaymentMethodEnumEntity.PIX)
                .providerChargeId("Not-used")
                .providerTxId(transactionId)//verificar se esse nome de atributo é o real retornado
                .providerStatus("lol")
                .amount(order.getTotalAmount())
                .status(PaymentStatusEnumEntity.PENDING)
                .idempotencyKey(UUID.randomUUID().toString())//gerar a lógica da idenpotence key
                .failureReason("lol")
                .rawResponse("{\"message\":\"lol\"}")
                .expiresAt(LocalDateTime.now().plusMinutes(10))
                .confirmedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

    }

    public static PaymentEntity creditCard(OrderEntity order, String chargeId, PaymentStatusEnumEntity initialStatus) {

        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        if (chargeId == null || chargeId.isBlank()) {
            throw new IllegalArgumentException("TransactionId cannot be null");
        }

        return PaymentEntity.builder()
                .orderEntity(order)
                .provider("Efí-Bank")
                .method(PaymentMethodEnumEntity.CREDIT_CARD)
                .providerChargeId(chargeId)
                .providerTxId("Not-used")//verificar se esse nome de atributo é o real retornado
                .providerStatus("lol")
                .amount(order.getTotalAmount())
                .status(initialStatus)
                .idempotencyKey(UUID.randomUUID().toString())//gerar a lógica da idenpotence key
                .failureReason("lol")
                .rawResponse("{\"message\":\"lol\"}")
                .expiresAt(LocalDateTime.now().plusMinutes(10))
                .confirmedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public void confirm () {
        providerStatus = "paid";
        status = PaymentStatusEnumEntity.APPROVED;
        confirmedAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        orderEntity.paid();
    }

    /*public void paid() {
        this.status = PaymentStatus.APPROVED;
        this.paidAt = OffsetDateTime.now();
    }*/

    public boolean isApproved () {
        return PaymentStatusEnumEntity.APPROVED.equals(this.status);
    }

}
