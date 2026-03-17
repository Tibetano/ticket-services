package com.anigame.ticket_services.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/events")
@RequiredArgsConstructor
public class EventController {

    @GetMapping("/{eventId}/batches/active")
    public ResponseEntity<?> getTicketInfo () {
        //return ResponseEntity.ok(ticketMapper.toTicketInfo());

        Map<String, Object> res = Map.of(
                "batch", Map.of(
                        "number", 1
                ),
                "tickets", List.of(
                        Map.of(
                                "type", "full",
                                "price", 30,
                                "totalQuantity", 100,
                                "soldQuantity", 50
                        ),
                        Map.of(
                                "type", "half",
                                "price", 15,
                                "totalQuantity", 50,
                                "soldQuantity", 30
                        )
                )
        );
        return ResponseEntity.ok(res);
    }

}
