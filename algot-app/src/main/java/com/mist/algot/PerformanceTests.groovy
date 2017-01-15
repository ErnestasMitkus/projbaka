package com.mist.algot

import com.mist.algot.PerformanceTester.TestEntry
import com.mist.algot.graphics.rendering.HidingManager

enum PerformanceTests {

    NO_ALGORITHMS({
        HidingManager.disableBackFaceCulling()
        HidingManager.disableZBuffer()
        HidingManager.disablePainters()
    }),
//    ONLY_BACKFACE_CULLING({
//        HidingManager.enableBackFaceCulling()
//        HidingManager.disableZBuffer()
//        HidingManager.disablePainters()
//    }),
//    ONLY_Z_BUFFER({
//        HidingManager.disableBackFaceCulling()
//        HidingManager.enableZBuffer()
//    }),
//    BACKFACE_AND_Z_BUFFER({
//        HidingManager.enableBackFaceCulling()
//        HidingManager.enableZBuffer()
//    })


    private final TestEntry testEntry

    PerformanceTests(Closure before) {
        this(new TestEntry(before, {}))
    }

    PerformanceTests(Closure before, Closure after) {
        this(new TestEntry(before, after))
    }

    PerformanceTests(TestEntry testEntry) {
        this.testEntry = testEntry
    }

    void before() { testEntry.before() }
    void after() { testEntry.after() }
}
