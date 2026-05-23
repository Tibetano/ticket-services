package com.anigame.ticket_services.infrastructure.payment;

import com.anigame.ticket_services.domain.Customer;
import com.anigame.ticket_services.domain.order.OrderEntity;
import com.anigame.ticket_services.infrastructure.clients.dto.PixChargeResponse;
import com.anigame.ticket_services.infrastructure.payment.dto.CardResponse;
import com.anigame.ticket_services.infrastructure.payment.dto.PixQRCodeResponse;
import com.anigame.ticket_services.usecase.webhook.dto.charge.DataDTO;
import com.anigame.ticket_services.web.dto.CreditCardDTO;

public interface PaymentGateway {
    PixChargeResponse generatePix(Customer customer, OrderEntity order);
    CardResponse generateChargeCard(Customer customer, OrderEntity order, CreditCardDTO card);
    PixQRCodeResponse generatePixQRCode(Long locId);
    DataDTO getPaymentStatus (String notificationToken);
}
