package com.scrabble.service;

import com.scrabble.model.Board;
import com.scrabble.model.Game;

public interface GameService {
    
    void addBoardToGame(Board board);

    boolean isWordValid(String word);

    static Integer getPoint(Character c) {
        Game game = Game.getInstance();
        return game.getLetterPoints().get(c);
    }
}
