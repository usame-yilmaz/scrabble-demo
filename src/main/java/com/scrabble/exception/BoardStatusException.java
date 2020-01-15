package com.scrabble.exception;

public class BoardStatusException extends RuntimeException{
    public BoardStatusException(final String message) {
        super(message);
    }
}
