package com.anigame.ticket_services.usecase.payment.strategy;

import com.anigame.ticket_services.domain.Customer;
import com.anigame.ticket_services.domain.PaymentEntity;
import com.anigame.ticket_services.domain.enums.PaymentMethodEnumEntity;
import com.anigame.ticket_services.domain.enums.PaymentStatusEnumEntity;
import com.anigame.ticket_services.domain.order.OrderEntity;
import com.anigame.ticket_services.infrastructure.payment.PaymentGateway;
import com.anigame.ticket_services.repository.payment.PaymentRepository;
import com.anigame.ticket_services.web.dto.request.PaymentRequestDTO;
import com.anigame.ticket_services.web.dto.response.CreditCardPaymentResponse;
import com.anigame.ticket_services.web.dto.response.OrderResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CreditCardPaymentStrategy implements PaymentStrategy {

    private final PaymentGateway gateway;
    private final PaymentRepository paymentRepository;
    @Value("${efi.credit-card-fee-value}")
    private BigDecimal creditCardFeeValue;

    @Override
    public PaymentMethodEnumEntity method() {
        return PaymentMethodEnumEntity.CREDIT_CARD;
    }

    @Override
    public OrderResponseDTO process(Customer customer, OrderEntity order, PaymentRequestDTO payment) {

        //aplica a taxa do serviço do efí ao valor do pedido
        order.applyPercentageFee(creditCardFeeValue);

        var response = gateway.generateChargeCard(customer, order, payment.creditCard());

        PaymentEntity entity = PaymentEntity.creditCard(order, response.data().chargeId().toString(), PaymentStatusEnumEntity.PENDING);

        paymentRepository.save(entity);

        var paymentResponse = CreditCardPaymentResponse.builder()
                .method(PaymentMethodEnumEntity.CREDIT_CARD)
                .status(entity.getStatus())
                .build();

        return OrderResponseDTO.builder()
                .orderNumber(order.getOrderNumber())
                .status(order.getStatus())
                .payment(paymentResponse)
                .build();
    }
}
