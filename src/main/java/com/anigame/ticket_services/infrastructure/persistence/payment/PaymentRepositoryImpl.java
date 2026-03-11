package com.anigame.ticket_services.infrastructure.persistence.payment;

import com.anigame.ticket_services.data.mapper.PaymentMapper;
import com.anigame.ticket_services.domain.new_impl.Payment;
import com.anigame.ticket_services.domain.persistence.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final SpringDataPaymentRepository jpaRepo;
    private final PaymentMapper paymentMapper;

    @Override
    public void save(Payment payment) {
        jpaRepo.save(
                paymentMapper.toEntityWithoutId(payment)
        );
    }
}
