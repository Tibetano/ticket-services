package com.anigame.ticket_services.shared.exception.exceptions;

import com.anigame.ticket_services.shared.exception.BusinessException;
import com.anigame.ticket_services.shared.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class InsufficientTicketsException extends BusinessException {

    public InsufficientTicketsException(String message, Object details) {
        super(
                message,
                ErrorCode.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR,
                details
        );
    }
}
