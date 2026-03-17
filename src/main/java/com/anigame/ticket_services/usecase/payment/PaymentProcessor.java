package com.anigame.ticket_services.usecase.payment;

import com.anigame.ticket_services.domain.Customer;
import com.anigame.ticket_services.domain.enums.PaymentMethodEnumEntity;
import com.anigame.ticket_services.usecase.payment.strategy.PaymentStrategy;
import com.anigame.ticket_services.web.dto.response.OrderResponseDTO;
import com.anigame.ticket_services.web.dto.request.PaymentRequestDTO;
import com.anigame.ticket_services.domain.OrderEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PaymentProcessor {

    private final Map<PaymentMethodEnumEntity, PaymentStrategy> strategies;

    public PaymentProcessor(List<PaymentStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(
                        PaymentStrategy::method,
                        Function.identity()
                ));
    }

    public OrderResponseDTO process(Customer customer, OrderEntity order, PaymentRequestDTO payment) {

        var strategy = strategies.get(payment.method());

        if (strategy == null) {
            //throw new BusinessException("Unsupported payment method");
            throw new RuntimeException("Unsupported payment method");
        }

        return strategy.process(customer, order, payment);
    }
}
