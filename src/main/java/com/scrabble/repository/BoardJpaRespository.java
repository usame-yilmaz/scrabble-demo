package com.scrabble.repository;

import com.scrabble.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface BoardJpaRespository extends JpaRepository<Board, Long>{

}
