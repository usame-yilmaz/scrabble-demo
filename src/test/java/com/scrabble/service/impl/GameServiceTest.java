package com.scrabble.service.impl;
import com.scrabble.model.Board;
import com.scrabble.model.Cell;
import com.scrabble.repository.BoardJpaRespository;
import com.scrabble.service.GameService;
import com.scrabble.util.BoardUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public  class GameServiceTest {

    @InjectMocks
    public GameService gameService = new GameServiceImpl();

    
    @Mock
    BoardJpaRespository boardJpaRespository;
    
    @Test
    public void addBoardToGameTest() {
        //given
        Long boardId = 1L;
        Board board = Board.builder().cells(new Cell[15][15]).playOrder(0).words(new HashSet<>()).cells(BoardUtils.initializeCells()).deactivated(false).build();
       
        //when
        when(boardJpaRespository.findOne(boardId)).thenReturn(board);
        
        try {
            gameService.addBoardToGame(board);
            assertTrue(true);
        } catch (Exception ex) {
            assertTrue(false);
        }
    }

}