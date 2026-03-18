package com.anigame.ticket_services.repository.payment;

import com.anigame.ticket_services.domain.PaymentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentRepository {

    private final SpringDataPaymentRepository jpaRepo;

    public void save(PaymentEntity payment) {
        jpaRepo.save(payment);
    }
}
