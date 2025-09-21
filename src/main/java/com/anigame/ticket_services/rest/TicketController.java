package com.anigame.ticket_services.rest;

import com.anigame.ticket_services.data.dto.TicketDTO;
import com.anigame.ticket_services.data.dto.TicketListDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @GetMapping
    public ResponseEntity<?> getUserTickets () {

        var exampleTicket = new TicketDTO(
                "AniGame 2024",
                "Ingresso Completo",
                LocalDate.now(),
                "Ativo",
                "https://via.placeholder.com/150x150/320%20100%2050/FFFFFF?text=QR"
        );

        var userTickets = new TicketListDTO(List.of(exampleTicket));
        return ResponseEntity.ok(userTickets);
    }
}
