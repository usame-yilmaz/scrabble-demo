package com.scrabble.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Move {
    
    private Character letter;
    private Integer row;
    private Integer column;

    @Override
    public String toString() {
        return "Letter: "+letter+" Row:" + row  +" Column:" + column;
    }
}
