package com.anigame.ticket_services.shared.exception;

import com.anigame.ticket_services.shared.exception.exceptions.BaaIntegrationException;
import com.anigame.ticket_services.shared.exception.exceptions.NotAvailableBatchException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaaIntegrationException.class)
    public ResponseEntity<?> handleDataAlreadyInUse (BaaIntegrationException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(NotAvailableBatchException.class)
    public ResponseEntity<?> handleAvailableBatch (NotAvailableBatchException exception) {
        System.out.println("Lançada a exeção NotAvailableBatchException com motivo: " + exception.getMessage());
        return ResponseEntity.ok(Map.of());
    }



    /*@ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRunTimeException (RuntimeException exception) {
        var response = new ExceptionResponseDTO(exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }*/

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusinessException (BusinessException ex,
                                                      HttpServletRequest request) {

        ApiError error = buildError(
                ex.getStatus().value(),
                ex.getErrorCode().name(),
                ex.getMessage(),
                request.getRequestURI(),
                ex.getDetails()
        );
        return ResponseEntity.status(ex.getStatus()).body(error);
    }

    private ApiError buildError(
            int status,
            String error,
            String message,
            String path,
            Object details
    ) {
        return ApiError.builder()
                .timestamp(Instant.now())
                .status(status)
                .error(error)
                .message(message)
                .path(path)
                .details(details)
                .build();
    }
}
