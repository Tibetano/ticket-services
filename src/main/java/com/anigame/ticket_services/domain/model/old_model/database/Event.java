package com.anigame.ticket_services.domain.model.old_model.database;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class Event {
    private UUID id;
    private Integer eventYear;
    private Integer version;
}
