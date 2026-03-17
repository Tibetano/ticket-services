package com.anigame.ticket_services.usecase.payment.strategy;

import com.anigame.ticket_services.domain.Customer;
import com.anigame.ticket_services.domain.PaymentEntity;
import com.anigame.ticket_services.domain.enums.PaymentMethodEnumEntity;
import com.anigame.ticket_services.domain.enums.PaymentStatusEnumEntity;
import com.anigame.ticket_services.infrastructure.payment.PaymentGateway;
import com.anigame.ticket_services.web.dto.response.CreditCardPaymentResponse;
import com.anigame.ticket_services.web.dto.response.OrderResponseDTO;
import com.anigame.ticket_services.web.dto.request.PaymentRequestDTO;
import com.anigame.ticket_services.repository.payment.PaymentRepository;
import com.anigame.ticket_services.domain.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditCardPaymentStrategy implements PaymentStrategy {

    private final PaymentGateway gateway;
    private final PaymentRepository paymentRepository;

    @Override
    public PaymentMethodEnumEntity method() {
        return PaymentMethodEnumEntity.CREDIT_CARD;
    }

    @Override
    public OrderResponseDTO process(Customer customer, OrderEntity order, PaymentRequestDTO payment) {

        var response = gateway.chargeCard(customer, order, payment.creditCard());

        PaymentEntity entity = PaymentEntity.creditCard(order, response.chargeId().toString(), PaymentStatusEnumEntity.PENDING);

        paymentRepository.save(entity);

        var paymentResponse = CreditCardPaymentResponse.builder()
                .method(PaymentMethodEnumEntity.CREDIT_CARD)
                .status(entity.getStatus())
                .build();


        return OrderResponseDTO.builder()
                .orderId(order.getId())
                .status(order.getStatus())
                .payment(paymentResponse)
                .build();
    }
}
