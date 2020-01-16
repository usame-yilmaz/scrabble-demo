package com.scrabble.util;

import com.scrabble.model.Cell;
import com.scrabble.model.Word;
import com.scrabble.service.GameService;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class BoardUtils {

    public static final int CELL_COUNT = 15;
    public static final String WORD_NOT_VALID = "Word is not valid.";
    public static final String BOARD_STATUS_NOT_VALID = "Board status is not valid.";

    private BoardUtils(){}

    // used to initialize cells with empty and occupied:false values
    // could be done in constructor of the Cell object
    // kept for being able to make specific modifications on initialization
    public static Cell[][] initializeCells() {
        Cell[][] cells = new Cell[CELL_COUNT][CELL_COUNT];
        for(int i=0;i<CELL_COUNT;i++) {
            for (int j = 0; j <CELL_COUNT ; j++) {
                cells[i][j] = Cell.builder().occupied(false).build();
            }
        }
        return cells;

    }

    // two dimensional array copy, using standard Arrays.copyOf static utility
    public static Cell[][] copyCells(Cell[][] cellsOriginal) {

        return Arrays.stream(cellsOriginal)
                .map(a ->  Arrays.copyOf(a, a.length))
                .toArray(Cell[][]::new);
    }

    // Scan given row for new words
    // if new words exists add to set
    //
    // NOTE: this algorithm could be improved, in terms of performance
    // if all moves(letters) are on the same row, below method will find same words multiple times
    // (set data structures provides uniqueness)
    public static void checkRowForNewWords(int posX, int posY, Cell[][] cells, Set<Word> words) {
        int xLeft = posX;
        int xRight = posX;
        while(xLeft>0 && cells[xLeft-1][posY].getOccupied().equals(true)) {
            xLeft--;
        }
        while(xRight<CELL_COUNT && cells[xRight+1][posY].getOccupied().equals(true)) {
            xRight++;
        }
        if(xLeft!=xRight) {
            StringBuilder sb = new StringBuilder();
            for(int i=xLeft;i<=xRight; i++) {
                sb.append(cells[i][posY].getCharacter());
            }
            addToWords(xLeft, posY, sb.toString(), words );
        }
    }

    // same comments above works for checkRowForNewWords method for columns as well
    public static void checkColumnForNewWords(int posX, int posY, Cell[][] cells, Set<Word> words) {
        int yUp = posY;
        int yBottom = posY;

        while(yBottom>0 && cells[posX][yBottom-1].getOccupied().equals(true)) {
            yBottom--;
        }
        while(yUp<CELL_COUNT && cells[posX][yUp+1].getOccupied().equals(true)) {
            yUp++;
        }
        if(yBottom!=yUp) {
            StringBuilder sb = new StringBuilder();
            for(int i=yBottom;i<=yUp; i++) {
                sb.append(cells[posX][i].getCharacter());
            }
            addToWords(posX, yBottom, sb.toString(), words );
        }
    }

    // add to words set
    private static void addToWords(int posX, int posY, String word, Set<Word> words) {
        words.add(
                Word.builder().characters(word).startRow(posX).startCol(posY).point(calculatePoints(word)).build());
    }

    // calculate points using given values
    private static Integer calculatePoints(String word) {
        return word.chars().mapToObj(c -> (char) c).map(GameService::getPoint).collect(Collectors.summingInt(Integer::intValue));

    }
    
}
