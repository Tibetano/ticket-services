package com.anigame.ticket_services.infrastructure.payment;

import com.anigame.ticket_services.domain.Customer;
import com.anigame.ticket_services.domain.order.OrderEntity;
import com.anigame.ticket_services.infrastructure.clients.dto.PixChargeResponse;
import com.anigame.ticket_services.infrastructure.payment.dto.CardResponse;
import com.anigame.ticket_services.usecase.webhook.dto.charge.DataDTO;
import com.anigame.ticket_services.web.dto.CreditCardDTO;
import com.anigame.ticket_services.web.dto.response.PixPaymentDetailsResponse;

import java.math.BigDecimal;

public interface PaymentGateway {
    PixChargeResponse generatePix(Customer customer, OrderEntity order, BigDecimal percentage);
    CardResponse generateChargeCard(Customer customer, OrderEntity order, CreditCardDTO card, BigDecimal percentage);
    PixPaymentDetailsResponse generatePixQRCode(Long locId);
    DataDTO getPaymentStatus (String notificationToken);
}
