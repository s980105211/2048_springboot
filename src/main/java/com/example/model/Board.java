package com.example.model;

import java.util.*;

public class Board {
    private final int SIZE = 4;
    private Tile[][] board;
    private int score;
    private boolean moved;
    private int maxnum;
    private List<Integer> mult;

    public Board() {
        board = new Tile[SIZE][SIZE];
        score = 0;
        addRandomTile();
        addRandomTile();
        maxnum = 2;
        mult = new ArrayList<>();
        mult.add(maxnum);
    }

    public List<List<Map<String, Object>>> getBoard() {
        List<List<Map<String, Object>>> result = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            List<Map<String, Object>> row = new ArrayList<>();
            for (int j = 0; j < SIZE; j++) {
                Tile tile = board[i][j];
                Map<String, Object> cell = new HashMap<>();
                cell.put("value", tile == null ? 0 : tile.getValue());
                cell.put("type", tile == null ? "normal" : tile.getType());
                if (tile != null && tile.getType().equals("special")) {
                    cell.put("life", tile.getLife());
                }
                if (tile != null) {
                    cell.put("displayAsPower", tile.isDisplayAsPower());
                }
                row.add(cell);
            }
            result.add(row);
        }
        return result;
    }

    public Map<String, Object> toResponseMap() {
        Map<String, Object> response = new HashMap<>();
        response.put("board", getBoard());
        response.put("score", getScore());
        response.put("gameOver", isGameOver());
        return response;
    }

    public int getScore() {
        return score;
    }

    public boolean move(String direction) {
        moved = false;

        switch (direction.toUpperCase()) {
            case "UP" -> moveUp();
            case "DOWN" -> moveDown();
            case "LEFT" -> moveLeft();
            case "RIGHT" -> moveRight();
            default -> throw new IllegalArgumentException("Invalid direction");
        }

        if (moved) {
            decreaseLife();
            addRandomTile();
        }

        return moved;
    }

    private void addRandomTile() {
        List<int[]> empty = new ArrayList<>();
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (board[i][j] == null ||
                        (board[i][j].getValue() == 0 && !"obstacle".equals(board[i][j].getType()))) {
                    empty.add(new int[] { i, j });
                }

        if (!empty.isEmpty()) {
            int[] pos = empty.get(new Random().nextInt(empty.size()));
            double rand = Math.random();
            Tile tile;

            if (rand < 0.05 && (mult.size() > 3)) { // 5% 機率生成特殊方塊
                int tileValue = mult.get((int) ((Math.random() * 100) % (mult.size() - 3)));
                tile = new Tile(tileValue, "special", tileValue);
            } else {
                int value = rand < 0.9 ? 2 : 4;
                tile = new Tile(value);
            }

            tile.setDisplayAsPower(Math.random() < 0.5); // 50% 機率顯示為 2^x
            board[pos[0]][pos[1]] = tile;
        }
    }

    private void decreaseLife() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Tile tile = board[i][j];
                if (tile != null && "special".equals(tile.getType())) {
                    tile.setLife(tile.getLife() - 1);
                    if (tile.getLife() <= 0) {
                        tile.setType("obstacle");
                        tile.setValue(-1);
                        tile.setLife(0);
                        tile.setDisplayAsPower(false); // ❗️重要：轉成 obstacle 時取消 power 顯示
                    }
                }
            }
        }
    }

    private void moveLeft() {
        for (int i = 0; i < SIZE; i++) {
            Tile[] original = board[i];
            Tile[] newRow = merge(original);

            if (!Arrays.equals(flatten(original), flatten(newRow))) {
                moved = true;
            }

            board[i] = newRow;
        }
    }

    private void moveRight() {
        for (int i = 0; i < SIZE; i++) {
            Tile[] original = board[i];
            Tile[] reversed = reverse(original);
            Tile[] newRow = merge(reversed);
            newRow = reverse(newRow);

            if (!Arrays.equals(flatten(original), flatten(newRow))) {
                moved = true;
            }

            board[i] = newRow;
        }
    }

    private void moveUp() {
        for (int col = 0; col < SIZE; col++) {
            Tile[] original = new Tile[SIZE];
            for (int row = 0; row < SIZE; row++) {
                original[row] = board[row][col];
            }

            Tile[] newCol = merge(original);

            if (!Arrays.equals(flatten(original), flatten(newCol))) {
                moved = true;
            }

            for (int row = 0; row < SIZE; row++) {
                board[row][col] = newCol[row];
            }
        }
    }

    private void moveDown() {
        for (int col = 0; col < SIZE; col++) {
            Tile[] original = new Tile[SIZE];
            for (int row = 0; row < SIZE; row++) {
                original[row] = board[row][col];
            }

            Tile[] reversed = reverse(original);
            Tile[] newCol = merge(reversed);
            newCol = reverse(newCol);

            if (!Arrays.equals(flatten(original), flatten(newCol))) {
                moved = true;
            }

            for (int row = 0; row < SIZE; row++) {
                board[row][col] = newCol[row];
            }
        }
    }

    private Tile[] merge(Tile[] line) {
        List<Tile> newLine = new ArrayList<>();
        // Slide 所有非 null 進來（包含 obstacle）
        for (Tile t : line) {
            if (t != null) {
                newLine.add(t);
            }
        }

        List<Tile> merged = new ArrayList<>();
        int i = 0;
        while (i < newLine.size()) {
            Tile curr = newLine.get(i);

            if (i + 1 < newLine.size()) {
                Tile next = newLine.get(i + 1);
                if (curr != null && next != null &&
                        curr.getValue() == next.getValue() &&
                        !"obstacle".equals(curr.getType()) &&
                        !"obstacle".equals(next.getType())) {

                    Tile newTile = new Tile(curr.getValue() * 2);
                    newTile.setDisplayAsPower(false);
                    merged.add(newTile);
                    score += newTile.getValue();
                    if (newTile.getValue() > maxnum) {
                        maxnum = newTile.getValue();
                        mult.add(maxnum);
                    }
                    i += 2;
                    continue;
                }
            }

            merged.add(curr);
            i++;
        }

        while (merged.size() < SIZE) {
            merged.add(null);
        }

        return merged.toArray(new Tile[SIZE]);
    }

    public boolean isGameOver() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Tile curr = board[i][j];

                if (curr == null || curr.getValue() == 0) {
                    return false;
                }

                // 可合併（非 obstacle）
                if (j < SIZE - 1) {
                    Tile right = board[i][j + 1];
                    if (right != null &&
                            curr.getValue() == right.getValue() &&
                            !"obstacle".equals(curr.getType()) &&
                            !"obstacle".equals(right.getType())) {
                        return false;
                    }
                }
                if (i < SIZE - 1) {
                    Tile down = board[i + 1][j];
                    if (down != null &&
                            curr.getValue() == down.getValue() &&
                            !"obstacle".equals(curr.getType()) &&
                            !"obstacle".equals(down.getType())) {
                        return false;
                    }
                }

                // 可滑動（有空格）
                if (j < SIZE - 1 && board[i][j + 1] == null)
                    return false;
                if (j > 0 && board[i][j - 1] == null)
                    return false;
                if (i < SIZE - 1 && board[i + 1][j] == null)
                    return false;
                if (i > 0 && board[i - 1][j] == null)
                    return false;
            }
        }
        return true;
    }

    private Tile[] reverse(Tile[] arr) {
        Tile[] reversed = new Tile[arr.length];
        for (int i = 0; i < arr.length; i++)
            reversed[i] = arr[arr.length - 1 - i];
        return reversed;
    }

    private int[] flatten(Tile[] tiles) {
        int[] values = new int[tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            values[i] = (tiles[i] == null) ? 0 : tiles[i].getValue();
        }
        return values;
    }
}
