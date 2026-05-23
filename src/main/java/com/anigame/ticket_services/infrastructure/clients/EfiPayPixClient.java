package com.anigame.ticket_services.infrastructure.clients;

import com.anigame.ticket_services.infrastructure.clients.dto.PixChargeRequest;
import com.anigame.ticket_services.infrastructure.clients.dto.PixChargeResponse;
import com.anigame.ticket_services.infrastructure.clients.dto.EfiPixQRCodeResponse;
import com.anigame.ticket_services.infrastructure.config.EfiPayPixFeignConfig;
import com.anigame.ticket_services.infrastructure.config.EfiMtlsConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        contextId = "efiPixClient",
        name = "efi-pix",
        url = "${efi.pix-base-url}",
        configuration = {
                EfiPayPixFeignConfig.class,
                EfiMtlsConfig.class
        }
)
public interface EfiPayPixClient {

    @PutMapping("/v2/cob/{txid}")
    PixChargeResponse createCharge(
            @PathVariable String txid,
            @RequestBody PixChargeRequest request
    );

    @GetMapping("/v2/loc/{id}/qrcode")
    EfiPixQRCodeResponse generateChargeQRCode(@PathVariable Long id);

}
