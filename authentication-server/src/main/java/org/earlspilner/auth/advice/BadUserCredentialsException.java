package org.earlspilner.auth.advice;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class BadUserCredentialsException extends RuntimeException {
    public BadUserCredentialsException(String message) {
        super(message);
    }
}
