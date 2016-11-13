package com.mist.algot.graphics.utils

class TimingUtils {

    static void time(String description, Closure closure) {
        Timer timer = startTimer()
        try {
            closure.call()
        } finally {
            timer.stop()
            describe(timer, description)
        }
    }

    static Timer startTimer() {
        new Timer()
    }

    static void describe(Timer timer, String description) {
        println description.replaceAll("%t", "${timer.elapsed()}ms")
    }

    public static class Timer {
        private final long startTime = System.currentTimeMillis()
        private long stopTime = -1

        public long stop() {
            if (stopTime < 0) {
                stopTime = System.currentTimeMillis()
            }
            stopTime
        }

        public long elapsed() {
            long until = stopTime < 0 ? System.currentTimeMillis() : stopTime
            until - startTime
        }
    }

}
