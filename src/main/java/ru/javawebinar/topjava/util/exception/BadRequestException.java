package ru.javawebinar.topjava.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Ognerezov on 12/11/16.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Incorrect request data")
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
