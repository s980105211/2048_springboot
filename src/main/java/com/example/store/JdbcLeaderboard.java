package com.example.store;

import com.example.model.ScoreEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class JdbcLeaderboard {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void submitScore(ScoreEntry entry) {
        jdbcTemplate.update(
                "INSERT INTO leaderboard (player, score) VALUES (?, ?)",
                entry.getPlayer(), entry.getScore());
    }

    public List<ScoreEntry> getTopScores() {
        return jdbcTemplate.query(
                "SELECT player, score FROM leaderboard ORDER BY score DESC LIMIT 100",
                new RowMapper<ScoreEntry>() {
                    @Override
                    public ScoreEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new ScoreEntry(rs.getString("player"), rs.getInt("score"));
                    }
                });
    }
}
