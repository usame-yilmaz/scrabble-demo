package com.scrabble.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public final ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex, WebRequest req) {
        ErrorResponse error = new ErrorResponse("Validation Exception: ", ex.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidWordException.class)
    public final ResponseEntity<ErrorResponse> handleInvalidWordException(InvalidWordException ex, WebRequest req) {
        ErrorResponse error = new ErrorResponse("Invalid word: ", ex.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(BoardStatusException.class)
    public final ResponseEntity<ErrorResponse> handleBoardStatusException(BoardStatusException ex, WebRequest req) {
        ErrorResponse error = new ErrorResponse("Board status not valid: ", ex.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
