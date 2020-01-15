package com.scrabble.model;

import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Word {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID", nullable = false)
    @NotFound(action= NotFoundAction.IGNORE)
    private Board board;

    private String characters;
    private Integer startRow;
    private Integer startCol;
    private PositionEnum position;
    private Integer point;

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Word)) {
            return false;
        }
        Word word = (Word)obj;
        return word.startCol.equals(startCol) && 
                word.startRow.equals(startRow) && 
                word.characters.equals(characters) &&
                word.point.equals(point);
    }

    @Override
    public int hashCode() {
        int result = 7;
        result += 31 * characters.hashCode();
        result += 31 * startCol.hashCode();
        result += 31 * startRow.hashCode();
        result += 31 * point.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Word: "+characters+" Points:" + point ;
    }
}
