package com.example.controller;

import com.example.model.*;
import com.example.service.GameService;
import com.example.store.InMemoryLeaderboard;
import com.example.store.JdbcLeaderboard;
import com.example.model.LeaderboardEntry;
import com.example.repository.LeaderboardRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class GameController {

    private final GameService gameService;
    private final LeaderboardRepository leaderboardRepository;

    public GameController(GameService gameService, LeaderboardRepository leaderboardRepository) {
        this.gameService = gameService;
        this.leaderboardRepository = leaderboardRepository;
    }

    @PostMapping("/start/{player}")
    public Map<String, Object> startGame(@PathVariable String player) {
        return gameService.startGame(player);
    }

    @PostMapping("/move/{player}")
    public Map<String, Object> move(@PathVariable String player, @RequestBody MoveRequest move) {
        return gameService.move(player, move.getDirection());
    }

    @GetMapping("/state/{player}")
    public Map<String, Object> getState(@PathVariable String player) {
        return gameService.getBoard(player);
    }

    @PostMapping("/restart/{player}")
    public Map<String, Object> restart(@PathVariable String player) {
        gameService.restart(player);
        return gameService.getBoard(player);
    }

    @PostMapping("/submit")
    public void submitScore(@RequestBody LeaderboardEntry entry) {
        leaderboardRepository.save(entry);
    }

    @GetMapping("/leaderboard")
    public List<LeaderboardEntry> getLeaderboard() {
        return leaderboardRepository.findTop100ByOrderByScoreDesc();
    }
}
