package com.mist.algot.graphics.utils

class PerformanceManager {

    private final long period

    private long lastSecondTime
    private int frames

    PerformanceManager(long periodInMs) {
        this.period = periodInMs
    }

    void registerFrame() {
        frames++
    }

    boolean periodPassed() {
        return (System.currentTimeMillis() - lastSecondTime) > period
    }

    void flush() {
        lastSecondTime += period
        frames = 0
    }

    int getFrames() {
        return frames
    }
}
