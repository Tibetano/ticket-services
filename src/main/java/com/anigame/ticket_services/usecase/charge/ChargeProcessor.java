package com.anigame.ticket_services.usecase.charge;

import com.anigame.ticket_services.usecase.charge.strategy.ChargeStatusHandler;
import com.anigame.ticket_services.usecase.webhook.dto.charge.DataDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ChargeProcessor {

    private final Map<String, ChargeStatusHandler> handlers;

    public ChargeProcessor (List<ChargeStatusHandler> handlerList) {
        this.handlers = handlerList.stream().collect(Collectors.toMap(
                ChargeStatusHandler::supports,
                Function.identity() // o método "Function.identity()" é a mesma coisa que "x -> x", ou seja, ela é apenas uma reescrita mais expreciva do caso onde temos que retornar o proprio objeto do stream.
        ));
    }

    public void process (DataDTO dataDTO) {

        ChargeStatusHandler handler = handlers.get(dataDTO.status().current());

        if (handler == null) {
            throw new RuntimeException("Unsupported handler-status-method");
        }

        handler.handle(dataDTO);
    }
}
