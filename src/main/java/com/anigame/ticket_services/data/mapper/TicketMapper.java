package com.anigame.ticket_services.data.mapper;

import com.anigame.ticket_services.data.dto.ticket.*;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {

    public TicketInfoDTO toTicketInfo () {

        var lot = new Lot(1);
        var fullTicket = new FullTicket(30,100,50);
        var halfTicket = new HalfTicket(50,30);
        var tickets = new Tickets(fullTicket,halfTicket);

        return new TicketInfoDTO(lot,tickets);
    }
}
