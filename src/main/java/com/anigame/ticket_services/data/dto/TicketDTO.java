package com.anigame.ticket_services.data.dto;

import java.time.LocalDate;

public record TicketDTO(
        String eventEdition,
        String ticketType,
        LocalDate date,
        String status,
        String qrCode
        ) {
}

/*
const mockTickets = [
    {
      id: '1',
      eventName: 'AniGame 2024',
      ticketType: 'Ingresso Completo',
      date: '7 de Julho, 2024',
      status: 'Ativo',
      qrCode: 'https://via.placeholder.com/150x150/320%20100%2050/FFFFFF?text=QR',
    },
  ];
 */