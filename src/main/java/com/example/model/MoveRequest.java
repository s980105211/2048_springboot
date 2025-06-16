package com.example.model;

public class MoveRequest {
    private String direction;

    public MoveRequest() {
    }

    public MoveRequest(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
