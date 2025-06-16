package com.example.service;

import com.example.model.Board;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GameService {
    private Map<String, Board> sessions = new HashMap<>();

    public Map<String, Object> startGame(String player) {
        Board board = new Board();
        sessions.put(player, board);
        return board.toResponseMap(); // ✅ 回傳完整資料
    }

    public Map<String, Object> getBoard(String player) {
        Board board = sessions.get(player);
        return board != null ? board.toResponseMap() : null;
    }

    public Map<String, Object> move(String player, String direction) {
        Board board = sessions.get(player);
        if (board != null) {
            board.move(direction);
            return board.toResponseMap(); // ✅ 執行移動後，回傳最新狀態
        }
        return null;
    }

    public void restart(String player) {
        sessions.put(player, new Board());
    }
}
