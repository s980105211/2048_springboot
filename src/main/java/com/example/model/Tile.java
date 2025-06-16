package com.example.model;

public class Tile {
    private int value;
    private String type; // "normal", "special", "obstacle"
    private int life;

    public Tile(int value) {
        this.value = value;
        this.type = "normal";
        this.life = 0;
    }

    public Tile(int value, String type, int life) {
        this.value = value;
        this.type = type;
        this.life = life;
    }

    private boolean displayAsPower = false; // 是否顯示為 2^x

    public boolean isDisplayAsPower() {
        return displayAsPower;
    }

    public void setDisplayAsPower(boolean displayAsPower) {
        this.displayAsPower = displayAsPower;
    }

    public int getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public int getLife() {
        return life;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLife(int life) {
        this.life = life;
    }
}
