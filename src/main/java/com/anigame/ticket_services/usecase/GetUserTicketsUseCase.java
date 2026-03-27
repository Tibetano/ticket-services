package com.anigame.ticket_services.usecase;

import com.anigame.ticket_services.domain.enums.TicketBatchTypeEnumEntity;
import com.anigame.ticket_services.domain.enums.TicketStatusEntity;
import com.anigame.ticket_services.web.dto.response.UserTicketListResponseDTO;
import com.anigame.ticket_services.web.dto.response.UserTicketResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetUserTicketsUseCase {

    public UserTicketListResponseDTO execute (String authorizationHeader) {


        return UserTicketListResponseDTO.builder()
                .ticketList(List.of(
                        UserTicketResponseDTO.builder()
                                .eventEdition("AniGame 2024")
                                .ticketType(TicketBatchTypeEnumEntity.FULL)
                                .date(LocalDate.parse("2026-02-24"))
                                .status(TicketStatusEntity.VALID)
                                .qrCode("https://via.placeholder.com/150x150/320%20100%2050/FFFFFF?text=QR")
                                .build(),
                        UserTicketResponseDTO.builder()
                                .eventEdition("AniGame 2025")
                                .ticketType(TicketBatchTypeEnumEntity.HALF)
                                .date(LocalDate.parse("2026-02-24"))
                                .status(TicketStatusEntity.VALID)
                                .qrCode("https://via.placeholder.com/150x150/320%20100%2050/FFFFFF?text=QR")
                                .build()
                ))
                .build();
    }
}
