package com.anigame.ticket_services.rest.controller;

import com.anigame.ticket_services.data.dto.creditCard.CreditCardPaymentDTO;
import com.anigame.ticket_services.data.dto.pix.CustomerPixInfoReqDTO;
import com.anigame.ticket_services.data.dto.Ticket2DTO;
import com.anigame.ticket_services.data.dto.TicketListDTO;
import com.anigame.ticket_services.data.mapper.CreditCardMapper;
import com.anigame.ticket_services.data.mapper.PixMapper;
import com.anigame.ticket_services.domain.usecase.GenerateChargeCreditCardUseCase;
import com.anigame.ticket_services.domain.usecase.GenerateChargePixUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class TicketController {

    private final GenerateChargePixUseCase generateChargePixUseCase;
    private final GenerateChargeCreditCardUseCase generateChargeCreditCardUseCase;

    @GetMapping("/ticket")
    public ResponseEntity<?> getUserTickets () {
        var exampleTicket = new Ticket2DTO("AniGame 2024", "Ingresso Completo", LocalDate.now(), "Ativo", "https://via.placeholder.com/150x150/320%20100%2050/FFFFFF?text=QR");
        var userTickets = new TicketListDTO(List.of(exampleTicket));
        return ResponseEntity.ok(userTickets);
    }

    @PostMapping("/cob/pix")
    public ResponseEntity<?> generateChargePix (@RequestBody CustomerPixInfoReqDTO customerPixInfo) throws URISyntaxException {
        var qrCodeData = PixMapper.toResDTO(generateChargePixUseCase.execute(customerPixInfo.fullName(), customerPixInfo.cpf()));
        return ResponseEntity.status(HttpStatus.OK).body(qrCodeData);
    }

    @PostMapping("/cob/credit-card")
    public ResponseEntity<?> generateCobCreditCard (@RequestBody CreditCardPaymentDTO creditCardPaymentDTO) {

        generateChargeCreditCardUseCase.execute(CreditCardMapper.CCardToDomain(creditCardPaymentDTO));

        //efetuar a lógica de gerar a cobrança via cartão e retornar as informações pertinentes.

        return ResponseEntity.status(HttpStatus.OK).body("Purchase successful.");
    }
}
