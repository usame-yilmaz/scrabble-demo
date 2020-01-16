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
import com.scrabble.service.GameService;
import com.scrabble.util.BoardUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public  class BoardServiceTest {

    @InjectMocks
    public BoardService boardService = new BoardServiceImpl();

//    @InjectMocks
//    public GameService gameService = new GameServiceImpl();
    
    @Mock
    BoardJpaRespository boardJpaRespository;

    @Mock
    GameServiceImpl gameService;
    
    @Test
    public void createBoardTest() {
        //given
        Long boardId = 1L;
        Board boardWithId = Board.builder().id(boardId).cells(new Cell[15][15]).playOrder(0).deactivated(false).build();

        //when
        when(boardJpaRespository.save(any(Board.class))).thenReturn(boardWithId);

        Board boardActual = boardService.createBoard();

        assertEquals(boardId, boardActual.getId());
    }

    @Test
    public void playTestWithValidWord() throws IOException{
        
        Word word = Word.builder().characters("kitap").point(25).position(PositionEnum.HORIZONTAL).build();
        Set<Word> wordSet = new HashSet<>();
        wordSet.add(word);

        List<Move> moves = new ArrayList<>();
        moves.add(Move.builder().letter('a').column(1).row(2).build());
        moves.add(Move.builder().letter('r').column(1).row(3).build());
        moves.add(Move.builder().letter('ı').column(1).row(4).build());
        
        Word result = Word.builder().characters("arı").startRow(2).startCol(1).point(4).build();
        
        playTest(moves, "arı", wordSet, true);

        // new wordset contains 2 elements
        assertEquals(wordSet.size(),2);

        // newly added word should be included in our word set
        // equals method of Word class guarantees that added object has equal: characters, startCol, startRow
        assertTrue(wordSet.contains(result));
    }

    @Test(expected = InvalidWordException.class)
    public void playTestWithInvalidWord() throws IOException{

        Set<Word> wordSet = new HashSet<>();

        List<Move> moves = new ArrayList<>();
        moves.add(Move.builder().letter('h').column(1).row(2).build());
        moves.add(Move.builder().letter('d').column(1).row(3).build());

        playTest(moves, "hd", wordSet, false);

    }


    private void playTest(List<Move> moves, String testWord, Set<Word> wordSet, boolean isWordValid) throws IOException{
        //given
        Long boardId = 1L;

        Board board = Board.builder().cells(new Cell[15][15]).playOrder(0).words(wordSet).cells(BoardUtils.initializeCells()).deactivated(false).build();
        
        //when
        when(boardJpaRespository.findOne(boardId)).thenReturn(board);
        when(gameService.loadDictionaries()).thenCallRealMethod();
        when(gameService.isWordValid(testWord)).thenReturn(isWordValid);
    
        // init dictionaries
        gameService.loadDictionaries();
        boardService.play(boardId, moves);
    }

    @Test
    public void getWordsTest() {

        //given
        Long boardId = 1L;
        
        Set<Word> wordSet = new HashSet<>();
        wordSet.add(Word.builder().characters("araba").point(25).position(PositionEnum.HORIZONTAL).build());
        wordSet.add(Word.builder().characters("kalem").point(15).position(PositionEnum.VERTICAL).build());
        wordSet.add(Word.builder().characters("kavun").point(18).position(PositionEnum.VERTICAL).build());
        
        Board board = Board.builder().cells(new Cell[15][15]).playOrder(0).words(wordSet).cells(BoardUtils.initializeCells()).deactivated(false).build();
        
        Set<String> expected = new HashSet<>();
        expected.add("Word: araba Points:25");
        expected.add("Word: kalem Points:15");
        expected.add("Word: kavun Points:18");
        
        //when
        when(boardJpaRespository.findOne(boardId)).thenReturn(board);

        Set<String> words = boardService.getWords(boardId);
        
        assertEquals(words.size(), 3);
        assertEquals(words, expected);
    }

    @Test
    public void setStatusTest() {
        //given
        Long boardId = 1L;
        StatusEnum statusExpected = StatusEnum.ACTIVE;
        Board board = Board.builder().deactivated(false).status(StatusEnum.PASSIVE).build();
        
        //when
        when(boardJpaRespository.findOne(boardId)).thenReturn(board);
        when( boardJpaRespository.save(any(Board.class))).thenReturn(board);
                
        boardService.setStatus(boardId, statusExpected);
        
        assertEquals(board.getStatus(), statusExpected);
    }

    @Test(expected = BoardStatusException.class)
    public void setStatusTestThrowsException() {
        //given
        Long boardId = 1L;
        Board board = Board.builder().deactivated(false).status(StatusEnum.DEACTIVATED).build();

        //when
        when(boardJpaRespository.findOne(boardId)).thenReturn(board);
        when( boardJpaRespository.save(any(Board.class))).thenReturn(board);

        // try to activate board
        boardService.setStatus(boardId, StatusEnum.ACTIVE);
    }

}