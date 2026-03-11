package com.anigame.ticket_services.domain.new_impl;

import com.anigame.ticket_services.z_domain.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Payment {

    private UUID id;
    private UUID orderId;
    private String provider;
    private PaymentMethod method;
    private String providerChargeId;
    private String providerTxId;
    private String providerStatus;
    private Integer amount;
    private PaymentStatus status;
    private String idempotencyKey;
    private String failureReason;
    private String rawResponse;
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Payment pix(OrderEntity order, String transactionId) {

        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        if (transactionId == null || transactionId.isBlank()) {
            throw new IllegalArgumentException("TransactionId cannot be null");
        }

            return Payment.builder()
                    .orderId(order.getId())
                    .provider("Efí-Bank")
                    .method(PaymentMethod.PIX)
                    .providerChargeId("Not-used")
                    .providerTxId(transactionId)//verificar se esse nome de atributo é o real retornado
                    .providerStatus("lol")
                    .amount(order.getTotalAmount())
                    .status(PaymentStatus.PENDING)
                    .idempotencyKey(UUID.randomUUID().toString())//gerar a lógica da idenpotence key
                    .failureReason("lol")
                    .rawResponse("{\"message\":\"lol\"}")
                    .expiresAt(LocalDateTime.now().plusMinutes(10))
                    .confirmedAt(LocalDateTime.now())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

    }

    public static Payment creditCard(OrderEntity order, String chargeId, PaymentStatus initialStatus) {

        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        if (chargeId == null || chargeId.isBlank()) {
            throw new IllegalArgumentException("TransactionId cannot be null");
        }

        return Payment.builder()
                .orderId(order.getId())
                .provider("Efí-Bank")
                .method(PaymentMethod.CREDIT_CARD)
                .providerChargeId("Not-used")
                .providerTxId(chargeId)//verificar se esse nome de atributo é o real retornado
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

    /*public void markAsPaid() {
        this.status = PaymentStatus.APPROVED;
        this.paidAt = OffsetDateTime.now();
    }*/

}