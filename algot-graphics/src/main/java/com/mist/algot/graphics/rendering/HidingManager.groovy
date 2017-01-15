package com.mist.algot.graphics.rendering

import org.lwjgl.opengl.GL11

class HidingManager {

    private static boolean backFaceCullingEnabled
    private static boolean ZBufferEnabled
    private static boolean paintersEnabled

    static boolean isBackFaceCullingEnabled() {
        return backFaceCullingEnabled
    }

    static boolean isZBufferEnabled() {
        return ZBufferEnabled
    }

    static boolean isPaintersEnabled() {
        return paintersEnabled
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
}
