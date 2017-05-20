package com.mist.algot.graphics.utils

class FailureUtils {

    static void doSilent(Closure closure) {
        try {
            closure.call()
        } catch (Exception ignored) {}
    }

}
