package com.mist.algot.graphics.rendering

import com.mist.algot.graphics.utils.PerformanceManager
import org.lwjgl.LWJGLException
import org.lwjgl.Sys
import org.lwjgl.opengl.ContextAttribs
import org.lwjgl.opengl.Display
import org.lwjgl.opengl.DisplayMode
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.PixelFormat

class DisplayManager {

    public static final int WIDTH = 1280
    public static final int HEIGHT = 720
    public static final int FPS = 120
    public static final String TITLE = "Algorithm Testing"

    private static PerformanceManager performanceManager

    public static void createDisplay() {
        createDisplay(null)
    }

    public static void createDisplay(int majorVersion, int minorVersion) {
        createDisplay(new Version(majorVersion, minorVersion))
    }

    public static void createDisplay(Version openGlVersion) {
        ContextAttribs attribs = null
        if (openGlVersion) {
            attribs = new ContextAttribs(openGlVersion.major, openGlVersion.minor)
//                .withForwardCompatible(true)
//                .withProfileCore(true)
        }

        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT))
            Display.setLocation(0, 0)
            Display.setTitle(TITLE)
            Display.create(new PixelFormat(), attribs)

            performanceManager = new PerformanceManager(1000)
        } catch (LWJGLException e) {
            e.printStackTrace()
        }

        GL11.glViewport(0, 0, WIDTH, HEIGHT)
    }

    public static PerformanceManager getPerformanceManager() {
        performanceManager
    }

    public static void prepare() {
        performanceManager.registerFrame()
    }

    public static void updateDisplay() {
        if (performanceManager.periodPassed()) {
            performanceManager.flush()
        }
        Display.sync(FPS)
        Display.update()
    }

    public static void closeDisplay() {
        Display.destroy()
    }

    public static class Version {
        private final int major
        private final int minor

        public Version(int major, int minor) {
            this.major = major
            this.minor = minor
        }
    }

}
