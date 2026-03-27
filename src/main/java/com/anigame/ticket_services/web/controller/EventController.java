package com.anigame.ticket_services.web.controller;

import com.anigame.ticket_services.usecase.GetActiveBatchUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final GetActiveBatchUseCase getActiveBatchUseCase;

    @GetMapping("/{eventId}/batches/active")
    public ResponseEntity<?> getActiveBatch () {
        return ResponseEntity.ok(getActiveBatchUseCase.execute());
    }

}
