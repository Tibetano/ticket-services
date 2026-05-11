package com.anigame.ticket_services.web.dto.response;

import com.anigame.ticket_services.domain.enums.TicketBatchTypeEnumEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record UserTicketListResponseDTO(
        @JsonProperty("ticket_list")
        List<UserTicketResponseDTO> ticketList
) {
        public static UserTicketListResponseDTO from (List<Object[]> ticketInfoList) {

                var list = ticketInfoList.stream().map(
                        row ->
                                new UserTicketResponseDTO(
                                        TicketBatchTypeEnumEntity.valueOf((String) row[1]),//ticket_type
                                        (String) row[2],//qr_code_hash
                                        "Edição " + ((BigDecimal)row[3])//year
                        ))
                        .toList();

                return new UserTicketListResponseDTO(
                        list
                );

        }

}
