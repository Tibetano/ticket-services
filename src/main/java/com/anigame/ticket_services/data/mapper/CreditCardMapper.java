package com.anigame.ticket_services.data.mapper;

import com.anigame.ticket_services.data.dto.creditCard.CreditCardDTO;
import com.anigame.ticket_services.data.dto.creditCard.CreditCardPaymentDTO;
import com.anigame.ticket_services.data.dto.creditCard.CustomerDTO;
import com.anigame.ticket_services.data.dto.creditCard.TicketDTO;
import com.anigame.ticket_services.domain.model.creditCard.*;
import org.springframework.stereotype.Component;

@Component
public class CreditCardMapper {

    //CreditCardPaymentDTO -> CreditCardCharge
    public static CreditCardCharge CCardToDomain (CreditCardPaymentDTO dto) {

        CreditCardCharge charge =  new CreditCardCharge();

        charge.setTickets(
                dto.tickets().stream()
                        .map(CreditCardMapper::TicketToDomain)
                        .toList());

        charge.setPayment(
                new Payment(CCardInfoToDomain(dto.creditCard()))
        );

        return charge;
    }

    public static Ticket TicketToDomain (TicketDTO dto) {
        return new Ticket(dto.type(),0, dto.amount());
    }

    public static CreditCard CCardInfoToDomain (CreditCardDTO dto) {
        return new CreditCard(CustomerToDomain(dto.customer()),dto.installments(),dto.paymentToken());
    }

    public static Customer CustomerToDomain (CustomerDTO dto) {
        return new Customer(dto.name(),dto.cpf(),dto.email(), dto.phoneNumber());
    }

}
