package com.example.model;

public class ScoreEntry implements Comparable<ScoreEntry> {
    private String player;
    private int score;

    public ScoreEntry() {
    }

    public ScoreEntry(String player, int score) {
        this.player = player;
        this.score = score;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int compareTo(ScoreEntry o) {
        return Integer.compare(o.score, this.score); // descending
    }
}
