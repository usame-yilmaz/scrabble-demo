package com.scrabble.exception;

public class InvalidWordException extends RuntimeException{
    public InvalidWordException(final String message) {
        super(message);
    }
}
