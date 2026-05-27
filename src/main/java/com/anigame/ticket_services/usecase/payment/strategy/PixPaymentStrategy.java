package com.anigame.ticket_services.usecase.payment.strategy;

import com.anigame.ticket_services.domain.Customer;
import com.anigame.ticket_services.domain.PaymentEntity;
import com.anigame.ticket_services.domain.enums.FeeType;
import com.anigame.ticket_services.domain.enums.PaymentMethodEnumEntity;
import com.anigame.ticket_services.domain.order.OrderEntity;
import com.anigame.ticket_services.infrastructure.payment.PaymentGateway;
import com.anigame.ticket_services.repository.payment.PaymentRepository;
import com.anigame.ticket_services.web.dto.request.PaymentRequestDTO;
import com.anigame.ticket_services.web.dto.response.OrderResponseDTO;
import com.anigame.ticket_services.web.dto.response.PixPaymentResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PixPaymentStrategy implements PaymentStrategy {

    private final ObjectMapper objectMapper;
    private final PaymentGateway gateway;
    private final PaymentRepository paymentRepository;
    @Value("${efi.pix-fee-value}")
    private BigDecimal pixFeeValue;

    @Override
    public PaymentMethodEnumEntity method() {
        return PaymentMethodEnumEntity.PIX;
    }

    @Override
    public OrderResponseDTO process(Customer customer, OrderEntity order, PaymentRequestDTO payment) {

        var response = gateway.generatePix(customer, order, pixFeeValue);

        String rawResponse;

        try {
            rawResponse = objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            System.out.println("DEU ERRO NA CONVERSÃO DO CARDRESPONSE PARA STRING NO ARQUIVO package com.anigame.ticket_services.usecase.payment.strategy; na PixPaymentStrategy");
            throw new RuntimeException(e);
        }

        PaymentEntity entity = PaymentEntity.pix(order, response, FeeType.PERCENTAGE, pixFeeValue, rawResponse);

        paymentRepository.save(entity);

        //gerar o pix qr-code aqui e retornar
        var pixQRCode = gateway.generatePixQRCode(response.getLoc().getId());

        //juntar esse dto abaixo com o dto acima
        var paymentResponse = PixPaymentResponse.builder()
                .method(PaymentMethodEnumEntity.PIX)
                .status(entity.getStatus())
                .details(pixQRCode)
                .build();

        return OrderResponseDTO.builder()
                .orderNumber(order.getOrderNumber())
                .status(order.getStatus())
                .payment(paymentResponse)
                .build();

    }

}
