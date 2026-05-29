package com.anigame.ticket_services.domain.order;

import com.anigame.ticket_services.domain.OrderItemEntity;
import com.anigame.ticket_services.domain.PaymentEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "orders",
        schema = "ticket_services"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid DEFAULT gen_random_uuid()", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "order_number", insertable = false, updatable = false)//ESSE ATRIBUTO NÃO PODE SER SETADO OU ATUALIZADO
    private Long orderNumber;//ESSE ATRIBUTO NÃO PODE SER SETADO OU ATUALIZADO
    @Column(name = "customer_id", nullable = false)
    private UUID customerId;
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false)
    private OrderStatusEntity status = OrderStatusEntity.PENDING;
    @Column(name = "total_amount", nullable = false)
    private Integer totalAmount;
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "orderEntity")
    private PaymentEntity paymentEntity;


    @Builder.Default
    @OneToMany(
            mappedBy = "orderEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OrderItemEntity> items = new ArrayList<>();


    public static OrderEntity createWithOrderItemList (UUID customerId,
                                      Long expiresAtTime,
                                      List<OrderItemEntity> orderItemList
    ) {

        Integer totalAmountTemp = orderItemList.stream()
                .mapToInt(item->item.getUnitPrice()*item.getQuantity())
                .sum();

        OrderEntity order = OrderEntity.builder()
                .customerId(customerId)
                .status(OrderStatusEntity.PENDING)
                .totalAmount(totalAmountTemp)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(expiresAtTime))
                .items(new ArrayList<>())
                .build();

                orderItemList.forEach(order::addItem);

                return order;
    }

    public void paid() {
        this.status = OrderStatusEntity.PAID;
    }

    public void paidByPix() {
        this.confirmReservedTickets();
        this.status = OrderStatusEntity.PAID;
    }

    public void confirmReservedTickets () {
        //chama o método "dar baixa" dos order entity que chamam o método confirm reserved tickets da entidade ticketbatchtypeentity
        this.items.forEach(OrderItemEntity::confirmReservedTickets);
    }

    public void expire() {
        this.status = OrderStatusEntity.EXPIRED;
    }

    public void cancel() {
        this.status = OrderStatusEntity.CANCELED;
    }

    public void addItem (OrderItemEntity item) {
        item.setOrderEntity(this);
        this.items.add(item);
    }

    /*public void applyPercentageFee(BigDecimal percentage) {
        if (paymentEntity.getMethod().equals(PaymentMethodEnumEntity.PIX)){
            BigDecimal fee = BigDecimal.valueOf(this.totalAmount)
                    .multiply(percentage)
                    .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);

            this.totalAmount = BigDecimal.valueOf(this.totalAmount)
                    .add(fee)
                    .setScale(0, RoundingMode.HALF_UP)
                    .intValue();
        } else if (paymentEntity.getMethod().equals(PaymentMethodEnumEntity.CREDIT_CARD)) {
            
        }
    }*/

}
