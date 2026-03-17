package com.anigame.ticket_services.domain;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class Customer {
    private UUID id;
    private String fullName;
    private String cpf;
    private String phoneNumber;
    private String email;
}
