package com.anigame.ticket_services.domain.new_impl;

import com.anigame.ticket_services.data.dto.OrderResponseDTO;
import com.anigame.ticket_services.data.dto.PaymentRequestDTO;
import com.anigame.ticket_services.z_domain.OrderEntity;

public interface PaymentStrategy {
    PaymentMethod method();
    OrderResponseDTO process(Customer customer, OrderEntity order, PaymentRequestDTO payment);
}
