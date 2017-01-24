package com.mist.algot

import com.mist.algot.graphics.utils.PerformanceManager

import java.util.concurrent.TimeUnit

import static com.mist.algot.Reporter.printLine

class PerformanceTester {

    private static final int EACH_TEST_DURATION = TimeUnit.MINUTES.toMillis(20)
//    private static final int EACH_TEST_DURATION = TimeUnit.SECONDS.toMillis(30)
    private static final int WARMUP_TIME = TimeUnit.MINUTES.toMillis(2)

    private static final List<PerformanceTests> tests

    static {
        tests = PerformanceTests.values()
    }

    static void doPerformanceTest(Closure gameLoop) {
        tests.each {
            it.before()
            performTest("$it", gameLoop)
            it.after()
        }
    }

    private static void performTest(String testName, Closure gameLoop) {
        PerformanceManager performanceManager = new PerformanceManager(1000)
        long startTime = System.currentTimeMillis()

        printLine "------------------------------"
        printLine "${new Date().format( 'yyyy-MM-dd HH:mm:ss' )} Starting test: $testName"
        printLine "Warming up..."

        // warm up the system
        while ((System.currentTimeMillis() - startTime < WARMUP_TIME) || !performanceManager.periodPassed()) {
            !performanceManager.periodPassed() ?: performanceManager.flush()
            performanceManager.registerFrame()
            gameLoop.call()
        }

        printLine "Warmup done."
        printLine "Analyzing performance..."

        // real test is here
        startTime = System.currentTimeMillis()
        List<Long> fpss = []
        while (System.currentTimeMillis() - startTime < EACH_TEST_DURATION) {
            performanceManager.registerFrame()
            gameLoop.call()
            if (performanceManager.periodPassed()) {
                fpss << performanceManager.frames
                performanceManager.flush()
            }
        }

        printLine "Analyzing done."

        // test is over. report timings
        long lowestFPS = 0
        long highestFPS = 0
        long averageFPS = 0

        int criticalsSize = (EACH_TEST_DURATION / 1000) / 20 //5% of TEST DURATION
        def unorderedFps = fpss.clone() as List<Long>
        fpss.sort()
        for (i in 0..(criticalsSize - 1)) {
            lowestFPS += fpss[i]
            highestFPS += fpss[-i - 1]
        }
        lowestFPS = Math.floorDiv(lowestFPS, criticalsSize)
        highestFPS = Math.floorDiv(highestFPS, criticalsSize)
        averageFPS = Math.floorDiv(fpss.sum() as Long, fpss.size())

        printLine "${new Date().format( 'yyyy-MM-dd HH:mm:ss' )}  Test Done. Results:"
        printLine "\tLowest FPS: $lowestFPS"
        printLine "\tHighest FPS: $highestFPS"
        printLine "\tAverage FPS: $averageFPS"
        printLine "All FPS values: $unorderedFps"
        printLine "------------------------------"
        printLine ""

        Reporter.flush()
    }

    static class TestEntry {
        private final Closure before, after

        TestEntry(Closure before, Closure after) {
            this.before = before
            this.after = after
        }

        void before() { before.call() }
        void after() { after.call() }
    }
}
