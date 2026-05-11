package com.anigame.ticket_services.usecase;

import com.anigame.ticket_services.infrastructure.security.TokenService;
import com.anigame.ticket_services.repository.ticket.TicketRepository;
import com.anigame.ticket_services.web.dto.response.UserTicketListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetUserTicketsUseCase {

    private final TicketRepository ticketRepository;
    private final TokenService tokenService;

    public UserTicketListResponseDTO execute (String authorizationHeader) {

        var userId = tokenService.getUserIdFromToken(authorizationHeader.replace("Bearer ",""));

        List<Object[]> userTicketList = ticketRepository.getTicketListByUserId(userId);

        return UserTicketListResponseDTO.from(userTicketList);
    }
}
