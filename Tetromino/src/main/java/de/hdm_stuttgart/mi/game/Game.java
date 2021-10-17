package de.hdm_stuttgart.mi.game;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm_stuttgart.mi.datamanager.DataManagerFactory;
import de.hdm_stuttgart.mi.exceptions.InvalidNumberOfClearedLinesException;
import de.hdm_stuttgart.mi.game.field.HoldField;
import de.hdm_stuttgart.mi.game.field.NextField;
import de.hdm_stuttgart.mi.game.field.PlayField;
import de.hdm_stuttgart.mi.game.score.Score;
import de.hdm_stuttgart.mi.game.score.ScoreType;
import de.hdm_stuttgart.mi.game.tetromino.Direction;
import de.hdm_stuttgart.mi.game.tetromino.Rotation;
import de.hdm_stuttgart.mi.game.tetromino.Tetromino;

public class Game {

    private static final Logger log = LogManager.getLogger(Game.class.getName());

    private final HoldField holdField = new HoldField();
    private final PlayField playField = new PlayField();
    private final NextField nextField = new NextField();
    private final Score score = new Score();

    private boolean gameOver = false;
    private boolean isSoftDrop = false;

    private final int period = DataManagerFactory.createGameSpeedManager("GameSpeedUserPreferencesManager").loadData();
    private final int speedPeriod = 50; // in milliseconds

    private final ScheduledExecutorService gameLoop = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = Executors.defaultThreadFactory().newThread(r);
            t.setDaemon(true);
            return t;
        }
    });
    private Future<?> gameLoopTask;

    public Game() {
        startGameLoop(period, period);
    }

    public HoldField getHoldField() {
        return holdField.copy();
    }

    public PlayField getPlayField() {
        return playField.copy();
    }

    public NextField getNextField() {
        return nextField.copy();
    }

    public Score getScore() {
        return score.copy();
    }

    public boolean isGameLoopRunning() {
        if (gameLoopTask != null) {
            return !gameLoopTask.isCancelled();
        } else {
            return false;
        }

    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getPeriod() {
        return period;
    }

    public void startGameLoop(long delay, long period) {
        gameLoopTask = gameLoop.scheduleAtFixedRate(() -> {

            log.debug("gameloop is running");

            if (playField.translateTetromino(Direction.DOWN)) {
                if (isSoftDrop) {
                    score.increase(ScoreType.SOFT_DROP);
                }
            } else {
                if (!playField.transferTetromino()) {
                    stopGameLoop();
                    gameOver = true;
                    log.info("GAME OVER");
                } else {
                    try {
                        int numberOfClearedLines = playField.clearFullLines();
                        score.lineIncrease(numberOfClearedLines);
                    } catch (InvalidNumberOfClearedLinesException e) {
                        log.warn(e);
                    }
                    playField.spawnTetromino(nextField.getNextTetromino());
                }
                holdField.setSwappable(true);
            }

        }, delay, period, TimeUnit.MILLISECONDS);
        log.debug("GameLoop started");
    }

    public void stopGameLoop() {
        gameLoopTask.cancel(false);
        log.debug("GameLoop stopped");
    }

    // GameInput
    public void translateTetrominoIfPossible(Direction direction) {
        if (isGameLoopRunning()) {
            playField.translateTetromino(direction);
        }
    }

    // GameInput
    public void rotateTetrominoIfPossible(Rotation rotation) {
        if (isGameLoopRunning()) {
            playField.rotateTetromino(rotation);
        }
    }

    // GameInput
    public void startSoftDrop() {
        if (isGameLoopRunning()) {
            stopGameLoop();
            startGameLoop(0, speedPeriod);
            isSoftDrop = true;
        }
    }

    // GameInput
    public void stopSoftDrop() {
        if (isGameLoopRunning()) {
            stopGameLoop();
            startGameLoop(period, period);
            isSoftDrop = false;
        }
    }

    // GameInput
    public void hardDrop() {
        if (isGameLoopRunning()) {
            stopGameLoop();
            while (playField.translateTetromino(Direction.DOWN)){
                score.increase(ScoreType.HARD_DROP);
            };
            startGameLoop(0, period);
        }
    }

    // GameInput
    public void swapTetrominoIfPossible() {
        if (isGameLoopRunning() && holdField.isSwappable()) {
            Tetromino tetromino = holdField.swapTetromino(playField.getTetromino());
            if (tetromino != null) {
                playField.spawnTetromino(tetromino);
            } else {
                playField.spawnTetromino(nextField.getNextTetromino());
            }
            holdField.setSwappable(false);
        }
    }

}
