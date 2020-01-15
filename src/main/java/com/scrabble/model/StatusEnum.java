package com.scrabble.model;

public enum StatusEnum {
    PASSIVE(1),
    ACTIVE(2),
    DEACTIVATED(3);

    private final int value;

    StatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}