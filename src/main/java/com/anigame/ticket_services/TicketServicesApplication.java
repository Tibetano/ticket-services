package com.anigame.ticket_services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TicketServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketServicesApplication.class, args);
	}

}
