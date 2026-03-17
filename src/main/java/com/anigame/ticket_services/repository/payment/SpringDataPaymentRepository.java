package com.anigame.ticket_services.repository.payment;

import com.anigame.ticket_services.domain.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataPaymentRepository extends JpaRepository<PaymentEntity, UUID> {
}
