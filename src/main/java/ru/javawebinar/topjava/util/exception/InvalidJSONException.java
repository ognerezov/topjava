package ru.javawebinar.topjava.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Ognerezov on 12/11/16.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid JSON formate")
public class InvalidJSONException extends RuntimeException {
    public InvalidJSONException(String message) {
        super(message);
    }
}
