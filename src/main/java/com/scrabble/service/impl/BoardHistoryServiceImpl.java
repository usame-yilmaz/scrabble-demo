package com.scrabble.service.impl;

import com.scrabble.model.Board;
import com.scrabble.model.BoardHistory;
import com.scrabble.model.Cell;
import com.scrabble.repository.BoardHistoryJpaRespository;
import com.scrabble.repository.BoardJpaRespository;
import com.scrabble.service.BoardHistoryService;
import com.scrabble.util.BoardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BoardHistoryServiceImpl implements BoardHistoryService {
    @Autowired
    BoardHistoryJpaRespository boardHistoryJpaRespository;

    @Autowired
    BoardJpaRespository boardJpaRespository;
    
    public void archive(Long boardId) {
        
        Board board = boardJpaRespository.findOne(boardId);
        String content = getContent(board);
        BoardHistory history = BoardHistory.builder().board(board).playOrder(board.getPlayOrder()).content(content).build();
        boardHistoryJpaRespository.save(history);
    }
    
    public String getBoardContent(Long boardId, Integer sequence) {
        Board board = boardJpaRespository.findOne(boardId);
        BoardHistory history = boardHistoryJpaRespository.findDistinctByBoardAndPlayOrder(board, sequence);
        return history.getContent().replace("-","<br>").replace(" ", "*");
    }

    private String getContent(Board board) {
        Cell[][] cells = board.getCells();
        
        StringBuilder sb = new StringBuilder();
        for(int j = 0; j<BoardUtils.CELL_COUNT; j++) {
            for(int i=0;i<15;i++){
                if(cells[i][j].getOccupied().equals(true)) {
                    sb.append(cells[i][j].getCharacter());
                } else {
                    sb.append(" ");
                }
            }
            sb.append("-");
        }
        return sb.toString();
    }
}
