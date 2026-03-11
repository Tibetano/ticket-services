package com.anigame.ticket_services.domain.new_impl;

import com.anigame.ticket_services.data.dto.CreditCardDTO;
import com.anigame.ticket_services.z_domain.OrderEntity;

public interface PaymentGateway {

    //PixResponse generatePix(Order order);
    PixResponse generatePix(Customer customer, OrderEntity order);

    CardResponse chargeCard(Customer customer, OrderEntity order, CreditCardDTO card);

    PixQRCodeResponse generatePixQRCode(String locId);
}
