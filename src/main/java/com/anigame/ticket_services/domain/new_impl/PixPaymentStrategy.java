package com.anigame.ticket_services.domain.new_impl;

import com.anigame.ticket_services.data.dto.OrderResponseDTO;
import com.anigame.ticket_services.data.dto.PaymentRequestDTO;
import com.anigame.ticket_services.data.dto.PixPaymentResponse;
import com.anigame.ticket_services.data.dto.PixPaymentResponseDetails;
import com.anigame.ticket_services.z_domain.OrderEntity;
import com.anigame.ticket_services.domain.persistence.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PixPaymentStrategy implements PaymentStrategy {

    private final PaymentGateway gateway;
    private final PaymentRepository paymentRepository;

    @Override
    public PaymentMethod method() {
        return PaymentMethod.PIX;
    }

    @Override
    public OrderResponseDTO process(Customer customer, OrderEntity order, PaymentRequestDTO payment) {

        var response = gateway.generatePix(customer, order);

        Payment entity = Payment.pix(order, response.txId());

        paymentRepository.save(entity);

        //gerar o pix qr-code aqui e retornar
        var pixQRCode = gateway.generatePixQRCode(response.locId());

        //juntar esse dto abaixo com o dto acima
        var paymentResponse = PixPaymentResponse.builder()
                .method(PaymentMethod.PIX)
                .status(entity.getStatus())
                .details(
                        PixPaymentResponseDetails.builder()
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
