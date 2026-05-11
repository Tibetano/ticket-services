package com.anigame.ticket_services.repository.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TicketRepository {

    private final SpringDataTicketRepository jpaRepo;

    public void generateTicketsByOrder(UUID orderId) {

        jpaRepo.generateTicketsByOrder(orderId);
    }

    public List<Object[]> getTicketListByUserId (UUID userId) {

        return jpaRepo.getTicketResume(userId);
    }

}
