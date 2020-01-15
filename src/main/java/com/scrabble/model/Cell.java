package com.scrabble.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Builder
@Setter
@Getter
public class Cell implements Serializable {
    private Boolean occupied = false;
    private Character character;

}
