package com.mist.algot.graphics.utils

class PerformanceManager {

    private final long period

    private long lastSecondTime = System.currentTimeMillis()
    private int frames

    private long lastDeltaTime = System.currentTimeMillis()
    private float delta

    PerformanceManager(long periodInMs) {
        this.period = periodInMs
    }

    void registerFrame() {
        frames++
        long now = System.currentTimeMillis()
        delta = (now - lastDeltaTime) / 1000f
        lastDeltaTime = now
    }

    boolean periodPassed() {
        return (System.currentTimeMillis() - lastSecondTime) > period
    }

    void flush() {
        lastSecondTime += period
        frames = 0
    }

    float getDelta() {
        return delta
    }

    int getFrames() {
        return frames
    }
}
