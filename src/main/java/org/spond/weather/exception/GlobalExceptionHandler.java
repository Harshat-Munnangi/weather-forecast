package org.spond.weather.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(Exception ex) {
        log.warn("Validation error: {}", ex.getMessage());
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation error",
                ex.getMessage()
        );
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpClientErrorException(HttpClientErrorException ex) {
        log.error("Client Error: {}", ex.getMessage());
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Client Error",
                ex.getMessage()
        );
    }

    @ExceptionHandler(HttpServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleHttpServerErrorException(HttpServerErrorException ex) {
        log.error("Server error: {}", ex.getMessage());
        return new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Server error",
                ex.getMessage()
        );
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleRuntimeException(RuntimeException ex) {
        log.error("Unexpected error: {}", ex.getMessage());
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Unexpected error",
                ex.getMessage()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneralException(Exception ex) {
        log.error("Internal server error: {}", ex.getMessage());
        return new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal server error",
                ex.getMessage()
        );
    }
}
