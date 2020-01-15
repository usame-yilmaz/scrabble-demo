package com.scrabble.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class Game {
    
    private List<Board> boards;
    private Set<String> dictionary;
    private Map<Character, Integer> letterPoints;
    private static Game ourInstance = new Game();

    public static Game getInstance() {
        return ourInstance;
    }

    private Game() {
        boards = new ArrayList<>();
    }
}
