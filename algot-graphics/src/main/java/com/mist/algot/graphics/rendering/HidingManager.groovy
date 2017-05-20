package com.mist.algot.graphics.rendering

import org.lwjgl.opengl.GL11

class HidingManager {

    private static boolean backFaceCullingEnabled
    private static boolean ZBufferEnabled
    private static boolean paintersEnabled
    private static boolean frustumCullingEnabled

    static boolean isBackFaceCullingEnabled() {
        return backFaceCullingEnabled
    }

    static boolean isZBufferEnabled() {
        return ZBufferEnabled
    }

    static boolean isPaintersEnabled() {
        return paintersEnabled
    }

    static boolean isFrustumCullingEnabled() {
        return frustumCullingEnabled
    }

    static void enableBackFaceCulling() {
        backFaceCullingEnabled = true
        GL11.glEnable(GL11.GL_CULL_FACE)
    }

    static void enableZBuffer() {
        ZBufferEnabled = true
        GL11.glEnable(GL11.GL_DEPTH_TEST)
        GL11.glDepthFunc(GL11.GL_LEQUAL)
        disablePainters()
    }

    static void enablePainters() {
        paintersEnabled = true
        GL11.glDepthFunc(GL11.GL_NEVER)
        disableZBuffer()
    }

    static void enableFrustumCulling() {
        frustumCullingEnabled = true
    }

    static void disableBackFaceCulling() {
        backFaceCullingEnabled = false
        GL11.glDisable(GL11.GL_CULL_FACE)
    }

    static void disableZBuffer() {
        ZBufferEnabled = false
        GL11.glDisable(GL11.GL_DEPTH_TEST)
    }

    static void disablePainters() {
        paintersEnabled = false
    }

    static void disableFrustumCulling() {
        frustumCullingEnabled = false
    }

    static void disableAll() {
        disableBackFaceCulling()
        disableZBuffer()
        disablePainters()
        disableFrustumCulling()
    }
}
