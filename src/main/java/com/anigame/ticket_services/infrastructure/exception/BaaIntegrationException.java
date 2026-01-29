package com.anigame.ticket_services.infrastructure.exception;

public class BaaIntegrationException extends RuntimeException {

    public BaaIntegrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
