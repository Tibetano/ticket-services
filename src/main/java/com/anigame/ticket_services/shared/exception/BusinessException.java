package com.anigame.ticket_services.shared.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;
    private final HttpStatus status;
    private final Object details;

    public BusinessException(
            String message,
            ErrorCode errorCode,
            HttpStatus status,
            Object details
    ) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
        this.details = details;
    }
}
