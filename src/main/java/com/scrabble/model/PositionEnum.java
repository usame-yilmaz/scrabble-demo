package com.scrabble.model;

public enum PositionEnum {
    HORIZONTAL(1),
    VERTICAL(2);

    private final int value;

    PositionEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}