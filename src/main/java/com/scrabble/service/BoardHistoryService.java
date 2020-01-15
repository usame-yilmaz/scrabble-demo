package com.scrabble.service;


public interface BoardHistoryService {
    
    void archive(Long boardId);
    
    String getBoardContent(Long boardId, Integer sequence);
}
