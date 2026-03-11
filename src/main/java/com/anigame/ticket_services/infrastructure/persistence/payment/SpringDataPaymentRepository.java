package com.anigame.ticket_services.infrastructure.persistence.payment;

import com.anigame.ticket_services.z_domain.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataPaymentRepository extends JpaRepository<PaymentEntity, UUID> {
}
