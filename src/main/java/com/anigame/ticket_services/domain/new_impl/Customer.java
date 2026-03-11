package com.anigame.ticket_services.domain.new_impl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class Customer {
    private UUID id;
    private String fullName;
    private String cpf;
    private String phoneNumber;
    private String email;
}
