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

    public PaymentEntity findByProviderChargeId (String providerChargeId){
        //System.out.println(providerChargeId);
        return jpaRepo.findByProviderChargeId(providerChargeId).orElseThrow(
                () -> new RuntimeException("Payment not found.")
        );
    }

    public PaymentEntity findByProviderTxId (String providerTxId){
        //System.out.println(providerTxId);
        return jpaRepo.findByProviderTxId(providerTxId).orElseThrow(
                () -> new RuntimeException("Payment not found.")
        );
    }
}
