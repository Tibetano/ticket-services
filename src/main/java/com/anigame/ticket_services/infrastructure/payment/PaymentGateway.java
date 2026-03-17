package com.anigame.ticket_services.infrastructure.payment;

import com.anigame.ticket_services.infrastructure.payment.dto.CardResponse;
import com.anigame.ticket_services.domain.Customer;
import com.anigame.ticket_services.infrastructure.payment.dto.PixQRCodeResponse;
import com.anigame.ticket_services.infrastructure.payment.dto.PixResponse;
import com.anigame.ticket_services.web.dto.CreditCardDTO;
import com.anigame.ticket_services.domain.OrderEntity;

public interface PaymentGateway {

    //PixResponse generatePix(Order order);
    PixResponse generatePix(Customer customer, OrderEntity order);

    CardResponse chargeCard(Customer customer, OrderEntity order, CreditCardDTO card);

    PixQRCodeResponse generatePixQRCode(String locId);
}
