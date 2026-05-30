package com.anigame.ticket_services.web.controller;

import com.anigame.ticket_services.usecase.webhook.ChargesWebhookUseCase;
import com.anigame.ticket_services.usecase.webhook.PixWebhookUseCase;
import com.anigame.ticket_services.usecase.webhook.dto.pix.PixWebhookDTO;
import com.anigame.ticket_services.web.dto.request.ChargeWebhookRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/webhook")
@RequiredArgsConstructor
public class WebhookController {

    private final ChargesWebhookUseCase chargesWebhookUseCase;
    private final PixWebhookUseCase pixWebhookUseCase;

    @PostMapping("/charges")
    public ResponseEntity<?> webhookCharge (@RequestParam("notification") ChargeWebhookRequestDTO chargeWebhook) {
        chargesWebhookUseCase.execute(chargeWebhook.notificationToken());
        return ResponseEntity.ok("{\"status\": 200}");
    }

    @PostMapping
    public ResponseEntity<?> webhookConfirmation () {
        return ResponseEntity.ok("{\"status\": 200}");
    }

    @PostMapping("/pix")
    public ResponseEntity<?> webhookPix (@RequestBody PixWebhookDTO dto) {
        pixWebhookUseCase.execute(dto);
        return ResponseEntity.ok("{\"status\": 200}");
    }
    
}
