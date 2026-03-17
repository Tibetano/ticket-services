package com.anigame.ticket_services.infrastructure.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("EventServicesClient")
public interface EventServicesFeignClient {

    //@PostMapping("/user/validate-user-info")
    //UserAttributeStatusDTO getUserDataAvailability (@RequestBody UserAttributeDTO userAttribute);

}
