package com.example.repository;

import com.example.model.PlayerScore;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ScoreRepository extends JpaRepository<PlayerScore, Long> {
    List<PlayerScore> findTop100ByOrderByScoreDesc();
}
