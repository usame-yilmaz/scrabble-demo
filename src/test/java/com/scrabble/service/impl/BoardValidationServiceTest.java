package com.scrabble.service.impl;
import com.scrabble.exception.BoardStatusException;
import com.scrabble.exception.InvalidWordException;
import com.scrabble.model.Board;
import com.scrabble.model.Cell;
import com.scrabble.model.Move;
import com.scrabble.model.PositionEnum;
import com.scrabble.model.StatusEnum;
import com.scrabble.model.Word;
import com.scrabble.repository.BoardJpaRespository;
import com.scrabble.service.BoardService;
import com.scrabble.service.BoardValidationService;
import com.scrabble.util.BoardUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public  class BoardValidationServiceTest {

    @InjectMocks
    public BoardValidationService boardValidationService = new BoardValidationServiceImpl();

//    @InjectMocks
//    public GameService gameService = new GameServiceImpl();
    
    @Mock
    BoardJpaRespository boardJpaRespository;

    @Mock
    GameServiceImpl gameService;
    
    @Test
    public void isAvailableForPlayTestWithActiveBoard() {
        //given
        Long boardId = 1L;
        Board boardWithId = Board.builder().id(boardId).status(StatusEnum.ACTIVE).build();
        
        
        //when
        when(boardJpaRespository.findOne(boardId)).thenReturn(boardWithId);

        boolean result = boardValidationService.isAvailableForPlay(boardId);
        
        assertTrue(result);
    }

    @Test
    public void isAvailableForPlayTestWithNonActiveBoard() {
        //given
        Long boardId = 99L;
        Board boardWithId = Board.builder().id(boardId).status(StatusEnum.DEACTIVATED).build();
        
        //when
        when(boardJpaRespository.findOne(boardId)).thenReturn(boardWithId);

        boolean result = boardValidationService.isAvailableForPlay(boardId);

        assertFalse(result);
    }

    @Test
    public void isAvailableForPlayTestWithNullBoard() {
        //given
        Long boardId = 88L;

        //when
        when(boardJpaRespository.save(any(Board.class))).thenReturn(null);

        boolean result = boardValidationService.isAvailableForPlay(boardId);

        assertFalse(result);
    }

    @Test
    public void isMoveOrderedTest() {
        List<Move> moves = new ArrayList<>();
        moves.add(Move.builder().letter('a').column(1).row(2).build());
        moves.add(Move.builder().letter('r').column(1).row(3).build());
        moves.add(Move.builder().letter('ı').column(1).row(4).build());


        boolean result = boardValidationService.isMoveOrdered(moves);

        assertTrue(result);
    }

    @Test
    public void isMoveOrderedTestFalseValue() {
        List<Move> moves = new ArrayList<>();
        moves.add(Move.builder().letter('e').column(1).row(2).build());
        moves.add(Move.builder().letter('f').column(5).row(3).build());
        moves.add(Move.builder().letter('d').column(1).row(4).build());


        boolean result = boardValidationService.isMoveOrdered(moves);

        assertFalse(result);
    }

    @Test
    public void isMoveValidTest() {
        //given
        Long boardId = 99L;
        Cell[][] cells = BoardUtils.initializeCells();
        cells[1][1]= Cell.builder().character('a').occupied(true).build();

        Board boardWithId = Board.builder().id(boardId).playOrder(1).status(StatusEnum.ACTIVE).cells(cells).build();

        List<Move> moves = new ArrayList<>();
        moves.add(Move.builder().letter('a').column(1).row(2).build());
        moves.add(Move.builder().letter('r').column(1).row(3).build());
        moves.add(Move.builder().letter('ı').column(1).row(4).build());

        //when
        when(boardJpaRespository.findOne(boardId)).thenReturn(boardWithId);

        boolean result = boardValidationService.isMoveValid(boardId, moves);

        assertTrue(result);
    }
    
}