package com.anigame.ticket_services.domain.persistence;

import com.anigame.ticket_services.domain.new_impl.Payment;

public interface PaymentRepository {
    void save (Payment payment);
}
