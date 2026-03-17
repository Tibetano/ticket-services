package com.anigame.ticket_services.infrastructure.clients.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserProfile {
    private UUID id;
    private String firstName;
    private String lastName;
    private String cpf;
    private String gender;
    private String dateOfBirth;
    private Instant createdAt;
}
