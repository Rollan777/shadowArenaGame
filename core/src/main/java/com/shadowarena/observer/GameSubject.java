package com.shadowarena.observer;

import java.util.ArrayList;
import java.util.List;

public class GameSubject {

    private final List<GameObserver> observers;

    public GameSubject() {
        this.observers = new ArrayList<>();
    }

    public void addObserver(GameObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String eventType, Object data) {
        for (GameObserver observer : observers) {
            observer.onNotify(eventType, data);
        }
    }
}
