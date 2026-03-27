package com.anigame.ticket_services.web.controller;

import com.anigame.ticket_services.usecase.CreateTicketUseCase;
import com.anigame.ticket_services.usecase.GetUserTicketsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class TicketController {

    private final GetUserTicketsUseCase getUserTicketsUseCase;
    private final CreateTicketUseCase createTicketUseCase;

    @GetMapping("/users/me/tickets")
    public ResponseEntity<?> getUserTickets (@RequestHeader("Authorization") String authorizationHeader) {
        return ResponseEntity.ok(getUserTicketsUseCase.execute(authorizationHeader));
    }

}
