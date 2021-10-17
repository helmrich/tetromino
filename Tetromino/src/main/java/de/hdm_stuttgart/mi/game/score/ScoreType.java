package de.hdm_stuttgart.mi.game.score;

public enum ScoreType {
    SINGLE(100), DOUBLE(300), TRIPLE(500), TETRIS(800), SOFT_DROP(1), HARD_DROP(2);

    private final int points;

    private ScoreType(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }
}