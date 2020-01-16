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

    // reference to board object
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID", nullable = false)
    @NotFound(action= NotFoundAction.IGNORE)
    private Board board;

    private String characters;
    private Integer startRow;
    private Integer startCol;
    private PositionEnum position;
    private Integer point;

    // equals and hashcode overridden for Collection operations
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Word)) {
            return false;
        }
        Word word = (Word)obj;
        return startCol.equals(word.startCol) && 
                startRow.equals(word.startRow) && 
                characters.equals(word.characters) &&
                point.equals(word.point);
    }

    @Override
    public int hashCode() {
        int result = 7;
        if(characters!=null) {
            result += 31 * characters.hashCode();
        } if(startCol!=null) {
            result += 31 * startRow.hashCode();
        } if(startRow!=null) {
            result += 31 * startCol.hashCode();
        } if(point!=null) {
            result += 31 * point.hashCode();
        }
        return result;
    }

    // to visualize Word object
    @Override
    public String toString() {
        return "Word: "+characters+" Points:" + point ;
    }
}
