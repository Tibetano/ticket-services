package com.anigame.ticket_services.usecase.payment.strategy;

import com.anigame.ticket_services.domain.Customer;
import com.anigame.ticket_services.domain.enums.PaymentMethodEnumEntity;
import com.anigame.ticket_services.web.dto.response.OrderResponseDTO;
import com.anigame.ticket_services.web.dto.request.PaymentRequestDTO;
import com.anigame.ticket_services.domain.OrderEntity;

public interface PaymentStrategy {
    PaymentMethodEnumEntity method();
    OrderResponseDTO process(Customer customer, OrderEntity order, PaymentRequestDTO payment);
}
