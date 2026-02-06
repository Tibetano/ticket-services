package com.anigame.ticket_services.domain.persistence;

import com.anigame.ticket_services.domain.model.database.Event;

public interface EventRepository {
    void save(Event event);
}
