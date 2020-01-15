package com.scrabble.controller;

import com.scrabble.aop.ScrabbleValidator;
import com.scrabble.model.Board;
import com.scrabble.model.Move;
import com.scrabble.model.StatusEnum;
import com.scrabble.service.BoardHistoryService;
import com.scrabble.service.BoardService;
import com.scrabble.service.GameService;
import com.scrabble.validation.PlayValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardHistoryService boardHistoryService;
    @Autowired
    private GameService gameService;

    /**
	 * Used to create a Board in DB
	 *
	 * @return the {@link com.scrabble.model.Board} created
	 */
    @PostMapping(value = "/createBoard")
    public ResponseEntity<Long> createBoard() {
        
        Board board = boardService.createBoard();
        gameService.addBoardToGame(board);
        
        return ResponseEntity.ok(board.getId());
    }

    /**
     * Used to play
     */
    @PostMapping(value = "/play")
    @ScrabbleValidator(validator = PlayValidator.CheckBoard.class)
    public ResponseEntity<Boolean> play(@RequestParam Long boardId, @RequestParam List<Move> moves) {
        boardService.play(boardId, moves);
        boardHistoryService.archive(boardId);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    /**
     * Get words of the board
     */
    @GetMapping(value = "/getWords")
    public ResponseEntity<Set<String>> getWords(@RequestParam Long boardId) {

        return ResponseEntity.ok(boardService.getWords(boardId));
    }

    /**
     * Get contents of the board with given sequence 
     */
    @GetMapping(value = "/getBoardContent")
    public ResponseEntity<String> getBoardContent(@RequestParam Long boardId, @RequestParam Integer sequence) {

        return ResponseEntity.ok(boardHistoryService.getBoardContent(boardId, sequence));
    }
    
    /**
     * Set status of the board
     */
    @PostMapping(value = "/setStatus")
    public void setStatus(@RequestParam Long boardId, @RequestParam StatusEnum status) {

        boardService.setStatus(boardId, status);
    }
}
