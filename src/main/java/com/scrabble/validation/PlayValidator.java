package com.scrabble.validation;

import com.scrabble.aop.ScrabbleValidationComponent;
import com.scrabble.controller.GameController;
import com.scrabble.model.Move;
import com.scrabble.service.BoardValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Map;

public class PlayValidator {

    private static final String BOARD_NOT_AVAILABLE = "Board status not valid for play";
    private static final String MOVE_NOT_VALID = "Not a valid move";
    private static final String MOVE_START_ERROR = "Move should start from an empty cell && adjacent to occupied cell";
    private static final String BOARD_NOT_EXISTS = "Board not exists";

    private PlayValidator() {
    }

    @ScrabbleValidationComponent
    public static class CheckPlay implements Validator {


        @Autowired
        BoardValidationService boardValidationService;

        CheckPlay() {
        }

        @Override
        public boolean supports(Class<?> aClass) {
            return GameController.class.equals(aClass);
        }

        @Override
        public void validate(Object o, Errors errors) {
            MapBindingResult result = (MapBindingResult) errors;
            // Get method parameters
            Map params = result.getTargetMap();
            // Find the parameter using its name and use it
            Long boardId = (Long) params.get("boardId");
            List<Move> moves = (List<Move>) params.get("moves");
                    
            // Board status should be active
            if(!boardValidationService.isAvailableForPlay(boardId)) {
                result.addError(new ObjectError("", BOARD_NOT_AVAILABLE));
            }

            // Letters should be ordered either horizontally or vertically (same row or same column)
            if(!boardValidationService.isMoveOrdered(moves)) {
                result.addError(new ObjectError("", MOVE_NOT_VALID));
            }
           
            // Move should start from an occupied cell, if not the first move 
            // Move coordinates(cells) should be empty
            if(!boardValidationService.isMoveValid(boardId, moves)) {
                result.addError(new ObjectError("", MOVE_START_ERROR));
            }
        }
    }

    @ScrabbleValidationComponent
    public static class CheckBoard implements Validator {


        @Autowired
        BoardValidationService boardValidationService;

        CheckBoard() {
        }

        @Override
        public boolean supports(Class<?> aClass) {
            return GameController.class.equals(aClass);
        }

        @Override
        public void validate(Object o, Errors errors) {
            MapBindingResult result = (MapBindingResult) errors;
            // Get method parameters
            Map params = result.getTargetMap();
            // Find the parameter using its name and use it
            Long boardId = (Long) params.get("boardId");

            // Board should exist
            if(!boardValidationService.isBoardExists(boardId)) {
                result.addError(new ObjectError("", BOARD_NOT_EXISTS));
            }

        }
    }
}
