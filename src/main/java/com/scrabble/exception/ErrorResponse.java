package com.scrabble.exception;

public class ErrorResponse
{
    public ErrorResponse(String message, String details) {
        super();
        this.message = message;
        this.details = details;
    }

    private String message;
    private String details;
}