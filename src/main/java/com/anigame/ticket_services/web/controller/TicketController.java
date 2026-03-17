package com.anigame.ticket_services.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/users/me/tickets")
@RequiredArgsConstructor
public class TicketController {

    @GetMapping
    public ResponseEntity<?> getUserTickets (@RequestHeader("Authorization") String authorizationHeader) {

        Map<String, Object> res = Map.of(
                "ticketList", List.of(
                        Map.of(
                                "eventEdition", "AniGame 2024",
                                "ticketType", "Ingresso Completo",
                                "date", "2026-02-24",
                                "status", "Ativo",
                                "qrCode", "https://via.placeholder.com/150x150/320%20100%2050/FFFFFF?text=QR"
                        )
                )
        );
        return ResponseEntity.ok(res);
    }

}
