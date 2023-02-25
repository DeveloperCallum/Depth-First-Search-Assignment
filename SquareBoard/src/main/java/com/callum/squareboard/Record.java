package com.callum.squareboard;

public class Record {
    private String key;
    private int score;
    private int level;

    public Record(String key, int score, int level) {
        this.key = key;
        this.score = score;
        this.level = level;
    }

    public String getKey() {
        return key;
    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }
}