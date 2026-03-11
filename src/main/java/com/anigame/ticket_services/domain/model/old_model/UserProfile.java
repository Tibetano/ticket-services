package com.anigame.ticket_services.domain.model.old_model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@Data
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
