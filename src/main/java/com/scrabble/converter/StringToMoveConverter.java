package com.scrabble.converter;


import com.scrabble.model.Move;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class StringToMoveConverter implements Converter<String, Move> {

    @Override
    public Move convert(String source) {
        String[] splitted = source.split(",");
        return new Move(splitted[0].charAt(0), Integer.valueOf(splitted[1]), Integer.valueOf(splitted[2]));

    }
}