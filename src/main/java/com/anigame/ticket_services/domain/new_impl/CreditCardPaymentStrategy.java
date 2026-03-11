package com.anigame.ticket_services.domain.new_impl;

import com.anigame.ticket_services.data.dto.CreditCardPaymentResponse;
import com.anigame.ticket_services.data.dto.OrderResponseDTO;
import com.anigame.ticket_services.data.dto.PaymentRequestDTO;
import com.anigame.ticket_services.z_domain.OrderEntity;
import com.anigame.ticket_services.domain.persistence.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditCardPaymentStrategy implements PaymentStrategy {

    private final PaymentGateway gateway;
    private final PaymentRepository paymentRepository;

    @Override
    public PaymentMethod method() {
        return PaymentMethod.CREDIT_CARD;
    }

    @Override
    public OrderResponseDTO process(Customer customer, OrderEntity order, PaymentRequestDTO payment) {

        var response = gateway.chargeCard(customer, order, payment.creditCard());

        Payment entity = Payment.creditCard(order, response.chargeId().toString(), PaymentStatus.PENDING);

        paymentRepository.save(entity);

        var paymentResponse = CreditCardPaymentResponse.builder()
                .method(PaymentMethod.CREDIT_CARD)
                .status(entity.getStatus())
                .build();


        return OrderResponseDTO.builder()
                .orderId(order.getId())
                .status(order.getStatus())
                .payment(paymentResponse)
                .build();
    }
}
