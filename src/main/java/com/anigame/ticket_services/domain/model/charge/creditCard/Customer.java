package com.anigame.ticket_services.domain.model.creditCard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {
    private String name;
    private String cpf;
    private String email;
    private String phoneNumber;
}
