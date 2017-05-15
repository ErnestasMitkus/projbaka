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
    public static final int FPS = 60
    public static final String TITLE = "Algorithm Testing"

    private static long lastFrameTime
    private static float delta

    private static boolean reportFPS = true

    private static final PerformanceManager performanceManager = new PerformanceManager(1000)

    public static void createDisplay(){
        ContextAttribs attribs = new ContextAttribs(3, 2) // TODO: 3, 2 on windows
            .withForwardCompatible(true)
//            .withProfileCore(true)

        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT))
            Display.setLocation(0, 0)
            Display.setTitle(TITLE)
            Display.create(new PixelFormat(), attribs)
        } catch (LWJGLException e) {
            e.printStackTrace()
        }

        GL11.glViewport(0, 0, WIDTH, HEIGHT)
        lastFrameTime = getCurrentTime()
    }

    public static void updateDisplay() {
        if (performanceManager.periodPassed()) {
            if (reportFPS) {
                println "FPS: $performanceManager.frames"
            }
            performanceManager.flush()
        }

//        Display.sync(FPS)
        Display.update()
    }

    public static void prepare() {
        performanceManager.registerFrame()
    }

    public static float getDelta() {
        performanceManager.delta
    }

    static PerformanceManager getPerformanceManager() {
        performanceManager
    }

    public static void closeDisplay() {
        Display.destroy()
    }

    private static long getCurrentTime() {
        return Sys.getTime()*1000 / Sys.getTimerResolution()
    }

    static void setReportFPS(boolean reportFPS) {
        this.reportFPS = reportFPS
    }

}
