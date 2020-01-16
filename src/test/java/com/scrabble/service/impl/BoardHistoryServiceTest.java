package com.scrabble.service.impl;
import com.scrabble.exception.BoardStatusException;
import com.scrabble.exception.InvalidWordException;
import com.scrabble.model.Board;
import com.scrabble.model.BoardHistory;
import com.scrabble.model.Cell;
import com.scrabble.model.Move;
import com.scrabble.model.PositionEnum;
import com.scrabble.model.StatusEnum;
import com.scrabble.model.Word;
import com.scrabble.repository.BoardHistoryJpaRespository;
import com.scrabble.repository.BoardJpaRespository;
import com.scrabble.service.BoardHistoryService;
import com.scrabble.service.BoardService;
import com.scrabble.util.BoardUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.validation.constraints.AssertTrue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public  class BoardHistoryServiceTest {

    @InjectMocks
    public BoardHistoryService boardHistoryService = new BoardHistoryServiceImpl();

    
    @Mock
    BoardJpaRespository boardJpaRespository;

    @Mock
    BoardHistoryJpaRespository boardHistoryJpaRespository;
    
    @Test
    public void archiveTest() {
        //given
        Long boardId = 1L;
        Board board = Board.builder().cells(new Cell[15][15]).playOrder(0).words(new HashSet<>()).cells(BoardUtils.initializeCells()).deactivated(false).build();
        Long historyId = 99L;
        BoardHistory history = BoardHistory.builder().board(board).playOrder(board.getPlayOrder()).content("").build();
        BoardHistory historyExpected = BoardHistory.builder().id(historyId).board(board).playOrder(board.getPlayOrder()).content("").build();
        
        //when
        when(boardJpaRespository.findOne(boardId)).thenReturn(board);
        when(boardHistoryJpaRespository.save(history)).thenReturn(historyExpected);

        try
        {
            boardHistoryService.archive(boardId);
            assertTrue(true);
        }
        catch (Exception ex) {
            assertTrue(false);
        }
        boardHistoryService.archive(boardId);
    }

    @Test
    public void getBoardContentTest() {
        //given
        Long boardId = 1L;
        Integer sequence = 1;
        Board board = Board.builder().cells(new Cell[15][15]).playOrder(0).words(new HashSet<>()).cells(BoardUtils.initializeCells()).deactivated(false).build();
        Long historyId = 99L;
        
        String content = "***************<br>***************<br>***************<br>***************<br>****ağaç*******<br>***************<br>***************<br>***************<br>***************<br>***************<br>***************<br>***************<br>***************<br>***************<br>***************<br>";
        
        BoardHistory history = BoardHistory.builder().id(historyId).board(board).playOrder(board.getPlayOrder()).content(content).build();

        //when
        when(boardJpaRespository.findOne(boardId)).thenReturn(board);
        when(boardHistoryJpaRespository.findDistinctByBoardAndPlayOrder(any(Board.class), any(Integer.class))).thenReturn(history);

        String result = boardHistoryService.getBoardContent(boardId, sequence);
        
        assertTrue(result.length()>0);
        assertEquals(result, history.getContent());
    }

}