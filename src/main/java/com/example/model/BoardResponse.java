package com.example.model;

import java.util.List;
import java.util.Map;

public class BoardResponse {
    private List<List<Map<String, Object>>> board;
    private int score;

    public BoardResponse(List<List<Map<String, Object>>> board, int score) {
        this.board = board;
        this.score = score;
    }

    public List<List<Map<String, Object>>> getBoard() {
        return board;
    }

    public int getScore() {
        return score;
    }
}
