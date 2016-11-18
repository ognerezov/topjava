package ru.javawebinar.topjava.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Ognerezov on 12/11/16.
 */
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "This email is allready in use")
public class DuplicateIdException extends RuntimeException {
    public DuplicateIdException(String message) {
        super(message);
    }
}
