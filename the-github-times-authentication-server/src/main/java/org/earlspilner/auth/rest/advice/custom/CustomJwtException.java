package org.earlspilner.auth.rest.advice.custom;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

public class CustomJwtException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String message;
    @Getter
    private final HttpStatus httpStatus;

    public CustomJwtException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
