package com.anigame.ticket_services.web.controller;

import com.anigame.ticket_services.usecase.CreateOrderUseCase;
import com.anigame.ticket_services.web.dto.request.OrderRequestDTO;
import com.anigame.ticket_services.web.dto.response.OrderResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(
            @RequestHeader("Authorization") String authorizationHeader,
            @Valid @RequestBody OrderRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createOrderUseCase.execute(authorizationHeader, request));
    }
}
