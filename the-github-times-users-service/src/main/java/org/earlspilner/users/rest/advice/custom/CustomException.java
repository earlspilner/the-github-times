package org.earlspilner.users.rest.advice.custom;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * @author Alexander Dudkin
 */
@Getter
public class CustomException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String message;
    private final HttpStatus httpStatus;

    public CustomException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

}
