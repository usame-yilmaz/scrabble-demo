package com.scrabble.service;

import com.scrabble.model.Move;

import java.util.List;

public interface BoardValidationService {
    
    boolean isAvailableForPlay(Long boardId);

    boolean isMoveOrdered(List<Move> moves);

    boolean isMoveValid(Long boardId, List<Move> moves);

    boolean isBoardExists(Long boardId);
    
}
