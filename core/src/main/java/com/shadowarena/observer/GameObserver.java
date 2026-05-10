package com.shadowarena.observer;

public interface GameObserver {

    void onNotify(String eventType, Object data);
}
