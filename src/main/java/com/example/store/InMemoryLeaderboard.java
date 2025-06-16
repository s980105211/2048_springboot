package com.example.store;

import com.example.model.ScoreEntry;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class InMemoryLeaderboard {
    private List<ScoreEntry> scores = new CopyOnWriteArrayList<>();

    public void submitScore(ScoreEntry entry) {
        scores.add(entry);
        Collections.sort(scores);
        if (scores.size() > 100)
            scores = scores.subList(0, 100);
    }

    public List<ScoreEntry> getTopScores() {
        return scores;
    }
}
