package com.anigame.ticket_services.usecase.payment.strategy;

import com.anigame.ticket_services.domain.Customer;
import com.anigame.ticket_services.domain.PaymentEntity;
import com.anigame.ticket_services.domain.enums.PaymentMethodEnumEntity;
import com.anigame.ticket_services.infrastructure.payment.PaymentGateway;
import com.anigame.ticket_services.web.dto.response.OrderResponseDTO;
import com.anigame.ticket_services.web.dto.request.PaymentRequestDTO;
import com.anigame.ticket_services.web.dto.response.PixPaymentResponse;
import com.anigame.ticket_services.web.dto.response.PixPaymentDetailsResponse;
import com.anigame.ticket_services.repository.payment.PaymentRepository;
import com.anigame.ticket_services.domain.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PixPaymentStrategy implements PaymentStrategy {

    private final PaymentGateway gateway;
    private final PaymentRepository paymentRepository;

    @Override
    public PaymentMethodEnumEntity method() {
        return PaymentMethodEnumEntity.PIX;
    }

    @Override
    public OrderResponseDTO process(Customer customer, OrderEntity order, PaymentRequestDTO payment) {

        var response = gateway.generatePix(customer, order);

        PaymentEntity entity = PaymentEntity.pix(order, response.txId());

        paymentRepository.save(entity);

        //gerar o pix qr-code aqui e retornar
        var pixQRCode = gateway.generatePixQRCode(response.locId());

        //juntar esse dto abaixo com o dto acima
        var paymentResponse = PixPaymentResponse.builder()
                .method(PaymentMethodEnumEntity.PIX)
                .status(entity.getStatus())
                .details(
                        PixPaymentDetailsResponse.builder()
                                .qrCodeImage(pixQRCode.qrCodeImage())
                                .qrCode(pixQRCode.qrCode())
                                .visualizationLink(pixQRCode.visualizationLink())
                                .build()
                )
                .build();

        return OrderResponseDTO.builder()
                .orderId(order.getId())
                .status(order.getStatus())
                .payment(paymentResponse)
                .build();
    }
}
