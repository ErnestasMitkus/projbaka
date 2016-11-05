package com.mist.algot

import com.mist.algot.graphics.rendering.DisplayManager
import com.mist.algot.graphics.rendering.Loader
import com.mist.algot.graphics.rendering.RawModel
import com.mist.algot.graphics.rendering.Renderer
import com.mist.algot.graphics.shaders.StaticShader
import org.apache.commons.lang3.SystemUtils
import org.lwjgl.opengl.Display

class Main {

    public static void main(String[] args) {
        System.setProperty("org.lwjgl.librarypath", determineNativesPath())

        DisplayManager.createDisplay()

        Loader loader = new Loader()
        Renderer renderer = new Renderer()
        StaticShader shader = new StaticShader()

        float[] squareVertices = [
            -0.5f,  0.5f, 0, //v0
            -0.5f, -0.5f, 0, //v1
             0.5f, -0.5f, 0, //v2
             0.5f,  0.5f, 0  //v3
        ]

        int[] squareIndices = [
            0, 1, 3,
            3, 1, 2
        ]
        RawModel squareModel = loader.loadToVAO(squareVertices, squareIndices)

        while (!Display.isCloseRequested()) {
            renderer.prepare()

            shader.start()
            renderer.render(squareModel)
            shader.stop()

            DisplayManager.updateDisplay()
        }

        shader.cleanup()
        loader.cleanup()
        DisplayManager.closeDisplay()
    }

    private static String determineNativesPath() {
        String natives = System.getProperty("user.dir") + "/natives"
        switch (true) {
            case SystemUtils.IS_OS_LINUX:
                return "$natives/linux"
            case SystemUtils.IS_OS_WINDOWS:
                return "$natives/windows"
            case SystemUtils.IS_OS_MAC_OSX:
                return "$natives/macosx"
            case SystemUtils.IS_OS_SOLARIS:
                return "$natives/solaris"
            default:
                throw new RuntimeException("Failed to determine natives to use for OS: " +
                        "$SystemUtils.OS_NAME, $SystemUtils.OS_VERSION, $SystemUtils.OS_ARCH")

        }
    }

}
