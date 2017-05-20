package com.mist.algot

import com.mist.algot.PerformanceTester.TestEntry
import com.mist.algot.graphics.rendering.HidingManager

enum PerformanceTests {

    NO_ALGORITHMS({
        HidingManager.disableAll()
    }),
    ONLY_BACKFACE_CULLING({
        HidingManager.disableAll()
        HidingManager.enableBackFaceCulling()
    }),
    ONLY_Z_BUFFER({
        HidingManager.disableAll()
        HidingManager.enableZBuffer()
    }),
    ONLY_FRUSTUM_CULLING({
        HidingManager.disableAll()
        HidingManager.enableFrustumCulling()
    }),
    BACKFACE_AND_FRUSTUM({
        HidingManager.disableAll()
        HidingManager.enableBackFaceCulling()
        HidingManager.enableFrustumCulling()
    }),
    BACKFACE_Z_AND_FRUSTUM({
        HidingManager.disableAll()
        HidingManager.enableBackFaceCulling()
        HidingManager.enableZBuffer()
        HidingManager.enableFrustumCulling()
    })


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
