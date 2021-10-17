package de.hdm_stuttgart.mi.input;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import de.hdm_stuttgart.mi.exceptions.UnassignedKeyException;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyInput {

    private final Scene scene;
    private final HashMap<KeyCode, Boolean> isPressedByKey = new HashMap<>();
    private final HashMap<KeyCode, ScheduledExecutorService> threadByKey = new HashMap<>();
    private final HashMap<KeyCode, Future<?>> scheduledTaskByKey = new HashMap<>();

    public KeyInput(Scene scene) {
        this.scene = scene;
    }

    public boolean isKeyPressed(KeyCode key) throws UnassignedKeyException {
        Boolean isKeyPressed = isPressedByKey.get(key);
        if (isKeyPressed == null) {
            throw new UnassignedKeyException(key + " is not assigned.");
        }
        return isKeyPressed;
    }

    public void addPressedEvent(KeyCode key, Runnable pressedHandle) {

        isPressedByKey.put(key, false);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (keyEvent) -> {
            if (keyEvent.getCode() == key && !isPressedByKey.get(key)) {
                pressedHandle.run();
                isPressedByKey.put(key, true);
            }
        });

        scene.addEventHandler(KeyEvent.KEY_RELEASED, (keyEvent) -> {
            if (keyEvent.getCode() == key) {
                isPressedByKey.put(key, false);
            }
        });

    }

    public void addReleasedEvent(KeyCode key, Runnable releasedHandle) {

        isPressedByKey.put(key, false);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (keyEvent) -> {
            if (keyEvent.getCode() == key && !isPressedByKey.get(key)) {
                isPressedByKey.put(key, true);
            }
        });

        scene.addEventHandler(KeyEvent.KEY_RELEASED, (keyEvent) -> {
            if (keyEvent.getCode() == key) {
                releasedHandle.run();
                isPressedByKey.put(key, false);
            }
        });

    }

    public void addPressedAndReleasedEvent(KeyCode key, Runnable pressedHandle, Runnable releasedHandle) {

        isPressedByKey.put(key, false);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (keyEvent) -> {
            if (keyEvent.getCode() == key && !isPressedByKey.get(key)) {
                pressedHandle.run();
                isPressedByKey.put(key, true);
            }
        });

        scene.addEventHandler(KeyEvent.KEY_RELEASED, (keyEvent) -> {
            if (keyEvent.getCode() == key) {
                releasedHandle.run();
                isPressedByKey.put(key, false);
            }
        });

    }

    public void addRepeatedEvent(KeyCode key, Runnable repeatedHandle, long initialDelay, long period) {

        isPressedByKey.put(key, false);
        threadByKey.put(key, Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = Executors.defaultThreadFactory().newThread(r);
                t.setDaemon(true);
                return t;
            }
        }));

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (keyEvent) -> {
            if (keyEvent.getCode() == key && !isPressedByKey.get(key)) {

                repeatedHandle.run();
                scheduledTaskByKey.put(key, threadByKey.get(key).scheduleAtFixedRate(() -> {
                    repeatedHandle.run();
                }, initialDelay, period, TimeUnit.MILLISECONDS));

                isPressedByKey.put(key, true);
            }
        });

        scene.addEventHandler(KeyEvent.KEY_RELEASED, (keyEvent) -> {
            if (keyEvent.getCode() == key) {
                scheduledTaskByKey.get(key).cancel(false);
                isPressedByKey.put(key, false);
            }
        });

    }

}
