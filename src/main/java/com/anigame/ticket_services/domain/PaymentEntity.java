package com.anigame.ticket_services.domain;

import com.anigame.ticket_services.domain.enums.FeePayerType;
import com.anigame.ticket_services.domain.enums.FeeType;
import com.anigame.ticket_services.domain.enums.PaymentMethodEnumEntity;
import com.anigame.ticket_services.domain.enums.PaymentStatusEnumEntity;
import com.anigame.ticket_services.domain.order.OrderEntity;
import com.anigame.ticket_services.infrastructure.clients.dto.PixChargeResponse;
import com.anigame.ticket_services.infrastructure.payment.dto.CardResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    @Column(name = "provider_fee_fixed")
    private Integer providerFeeFixed;
    @Column(name = "provider_fee_percentage", precision = 5, scale = 2)
    private BigDecimal providerFeePercentage;
    @Column(name = "provider_fee_amount")
    private Integer providerFeeAmount;
    @Column(name = "net_amount")
    private Integer netAmount;
    @Column(name = "fee_payer")
    private FeePayerType feePayer;



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


    public static PaymentEntity pix(
            OrderEntity order,
            PixChargeResponse pixChargeResponse,
            FeeType feeType,
            BigDecimal pixFeeValue,
            String rawResponse
    ) {

        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        if (pixChargeResponse.getTxid() == null || pixChargeResponse.getTxid().isBlank()) {
            throw new IllegalArgumentException("TransactionId cannot be null");
        }

        Integer totalAmount = new BigDecimal(pixChargeResponse.getAmount().getOriginalAmount()).multiply(BigDecimal.valueOf(100)).intValue();
        Integer providerFeeAmount = BigDecimal.valueOf(totalAmount)
                .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP)
                .setScale(0, RoundingMode.HALF_UP)
                .intValue();

        return PaymentEntity.builder()
                .orderEntity(order)
                .provider("Efí-Bank")
                .method(PaymentMethodEnumEntity.PIX)
                .providerChargeId("Not-used")
                .providerTxId(pixChargeResponse.getTxid())
                .providerStatus(pixChargeResponse.getStatus())



                .amount(totalAmount)

                .providerFeeFixed(feeType.equals(FeeType.FIXED) ? pixFeeValue.intValue() : 0)
                .providerFeePercentage(feeType.equals(FeeType.PERCENTAGE) ? pixFeeValue : new BigDecimal(0))
                .providerFeeAmount(providerFeeAmount)
                .netAmount(totalAmount - providerFeeAmount)
                .feePayer(FeePayerType.MERCHANT)




                .status(PaymentStatusEnumEntity.PENDING)
                .idempotencyKey(UUID.randomUUID().toString())//gerar a lógica da idenpotence key
                .failureReason("Not-implemented")
                .rawResponse(rawResponse)
                .expiresAt(LocalDateTime.now().plusMinutes(10))
                .confirmedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

    }

    public static PaymentEntity creditCard(
            OrderEntity order,
            CardResponse cardResponse,
            PaymentStatusEnumEntity initialStatus,
            FeeType feeType,
            BigDecimal creditCardFeeValue,
            String rawResponse
    ) {

        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        if (cardResponse.data().chargeId() == null || cardResponse.data().chargeId().toString().isBlank()) {
            throw new IllegalArgumentException("TransactionId cannot be null");
        }

        int providerFeeAmount = BigDecimal.valueOf(cardResponse.data().total())
                .multiply(creditCardFeeValue)
                .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP)
                .setScale(0, RoundingMode.HALF_UP)
                .intValue();

        return PaymentEntity.builder()
                .orderEntity(order)
                .provider("Efí-Bank")
                .method(PaymentMethodEnumEntity.CREDIT_CARD)
                .providerChargeId(cardResponse.data().chargeId().toString())
                .providerTxId("Not-used")
                .providerStatus(cardResponse.data().status())


                .amount(cardResponse.data().total())
                .providerFeeFixed(feeType.equals(FeeType.FIXED) ? creditCardFeeValue.intValue() : 0)
                .providerFeePercentage(feeType.equals(FeeType.PERCENTAGE) ? creditCardFeeValue : new BigDecimal(0))
                .providerFeeAmount(providerFeeAmount)
                .netAmount(cardResponse.data().total() - providerFeeAmount)
                .feePayer(FeePayerType.MERCHANT)


                .status(initialStatus)
                .idempotencyKey(UUID.randomUUID().toString())//gerar a lógica da idenpotence key
                .failureReason("Not-implemented")
                .rawResponse(rawResponse)
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
