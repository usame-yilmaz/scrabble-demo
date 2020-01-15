package com.scrabble.service.impl;

import com.scrabble.exception.BoardStatusException;
import com.scrabble.exception.InvalidWordException;
import com.scrabble.model.Board;
import com.scrabble.model.Cell;
import com.scrabble.model.Move;
import com.scrabble.model.StatusEnum;
import com.scrabble.model.Word;
import com.scrabble.repository.BoardJpaRespository;
import com.scrabble.repository.WordJpaRespository;
import com.scrabble.service.BoardService;
import com.scrabble.util.BoardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class BoardServiceImpl implements BoardService {
    @Autowired
    BoardJpaRespository boardJpaRespository;
    @Autowired
    WordJpaRespository wordJpaRespository;
    @Autowired
    GameServiceImpl gameService;
    
    public Board createBoard() {
        Board board = Board.builder().playOrder(0).deactivated(false).cells(BoardUtils.initializeCells()).status(StatusEnum.PASSIVE).build();
        return boardJpaRespository.save(board);
    }

    public void play(Long boardId, List<Move> moves) {
        Board board = getBoardFromDb(boardId);

        Cell[][] cells = BoardUtils.copyCells(board.getCells());
        
        for(Move move:moves) {
            Cell cell = Cell.builder().occupied(true).character(move.getLetter()).build();
            cells[move.getRow()][move.getColumn()] = cell;
        }

        Set<Word> words = board.getWords();
        Set<Word> newWords = findNewWords(cells, moves);

        boolean newWordsValid = newWords.stream().noneMatch(
                word -> !gameService.isWordValid(word.getCharacters()) || words.contains(word));

        if(newWordsValid) {
            newWords.stream().map(word -> {word.setBoard(board); return word; }).collect(Collectors.toSet());
            board.setCells(cells);
            words.addAll(newWords);

            board.setPlayOrder(board.getPlayOrder()+1);
            boardJpaRespository.save(board);
        } else {
            throw new InvalidWordException(BoardUtils.WORD_NOT_VALID);
        }


    }

    private Set<Word> findNewWords(Cell[][] cells, List<Move> moves) {

        Set<Word> words = new HashSet<>();

        for(Move move:moves) {
            int posX = move.getRow();
            int posY = move.getColumn();

            BoardUtils.checkRowForNewWords(posX, posY, cells, words);
            BoardUtils.checkColumnForNewWords(posX, posY, cells, words);
        }
        return words;
    }


    public Set<String> getWords(Long boardId) {
        
        Board board = getBoardFromDb(boardId);
        return board.getWords().stream().map(Word::toString).collect(Collectors.toSet());
    }

    public void setStatus(Long boardId, StatusEnum status) {

        Board board = getBoardFromDb(boardId);
        if(board != null && !StatusEnum.DEACTIVATED.equals(board.getStatus())) {
            board.setStatus(status);
            boardJpaRespository.save(board);
        } else {
            throw new BoardStatusException(BoardUtils.BOARD_STATUS_NOT_VALID);
        }
    }

    
    private Board getBoardFromDb(Long boardId){
        return boardJpaRespository.findOne(boardId);
    }
}
