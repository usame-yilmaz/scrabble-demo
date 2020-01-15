package com.scrabble.service;


import com.scrabble.model.Board;
import com.scrabble.model.Move;
import com.scrabble.model.StatusEnum;

import java.util.List;
import java.util.Set;

public interface BoardService {
    
    Board createBoard();

    void play(Long boardId, List<Move> moves);

    Set<String> getWords(Long boardId);

    void setStatus(Long boardId, StatusEnum status);

}
