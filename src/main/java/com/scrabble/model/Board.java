package com.scrabble.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import java.util.Set;

import static com.scrabble.util.BoardUtils.CELL_COUNT;


@Entity
@Builder
@Setter
@Getter
@EqualsAndHashCode(of = {"id", "status"})
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private StatusEnum status;
    private boolean deactivated;
    private Integer playOrder;

    @Lob
    private Cell[][] cells = new Cell[CELL_COUNT][CELL_COUNT];
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "board")
    private Set<Word> words;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "board")
    private Set<BoardHistory> history;

}
