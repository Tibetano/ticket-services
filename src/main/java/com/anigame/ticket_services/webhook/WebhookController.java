package com.anigame.ticket_services.webhook;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class WebhookController {

    private final WebhookService webhookService;

    @PostMapping("/webhooks/payment")
    public ResponseEntity<Void> webhook(@RequestBody WebhookDTO dto) {
        webhookService.process(dto);
        return ResponseEntity.ok().build();
    }

}
