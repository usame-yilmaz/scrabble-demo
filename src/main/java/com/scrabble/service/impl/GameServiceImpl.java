package com.scrabble.service.impl;

import com.scrabble.model.Board;
import com.scrabble.model.Game;
import com.scrabble.service.GameService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

@Service
@Transactional
public class GameServiceImpl implements GameService {
    
    public void addBoardToGame(Board board) {
        // Get instance from singleton object
        Game game = Game.getInstance();
        game.getBoards().add(board);
    }

    @PostConstruct
    private void loadDictionaries() throws IOException {
        // Get instance from singleton object
        Game game = Game.getInstance();
        game.setDictionary(loadWords());
        game.setLetterPoints(loadPoints());
    }
    
    private Set<String> loadWords() throws IOException{
        Set<String> dictionary = new HashSet<>();
        try (Stream<String> stream = Files.lines(Paths.get("src/main/resources/scrabble_turkish_dictionary.txt"))) {
            stream.forEach(dictionary::add);
        }
        return dictionary;
    }

    private Map<Character, Integer> loadPoints() throws IOException{
        Map<Character, Integer> pointsMap = new HashMap<>();
        try (Stream<String> stream = Files.lines(Paths.get("src/main/resources/points.txt"))) {
            stream.forEach(line -> {
                String[] lineSplitted = line.split("-");
                pointsMap.putIfAbsent(lineSplitted[0].charAt(0), Integer.parseInt(lineSplitted[1]));
            });
        }
        return pointsMap;
    }

    public boolean isWordValid(String word) {

        Game game = Game.getInstance();
        return game.getDictionary().contains(word);
    }
    
}
