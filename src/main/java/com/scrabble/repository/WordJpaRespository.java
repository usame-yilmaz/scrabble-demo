package com.scrabble.repository;

import com.scrabble.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface WordJpaRespository extends JpaRepository<Word, Long>{

}
