package com.mist.algot

import com.mist.algot.graphics.controls.KeyboardManager
import com.mist.algot.graphics.entities.Camera
import com.mist.algot.graphics.entities.Entity
import com.mist.algot.graphics.entities.Light
import com.mist.algot.graphics.rendering.DisplayManager
import com.mist.algot.graphics.rendering.Loader
import com.mist.algot.graphics.rendering.MasterRenderer
import org.apache.commons.lang3.SystemUtils
import org.lwjgl.opengl.Display
import org.lwjgl.util.vector.Vector3f

class Main {

    static void main(String[] args) { // example program arguments foo.txt trees
        System.setProperty("org.lwjgl.librarypath", determineNativesPath())
//        args = ["/home/ernestas/Desktop/bakatest/foo.txt"]
        if (args.length > 0) {
            Reporter.setupReporter(args[0])
        }

        DisplayManager.createDisplay()
        DisplayManager.reportFPS = true

        Loader loader = new Loader()
        Scenarios.loadModels(loader)
        MasterRenderer renderer = new MasterRenderer()
        Camera camera = new Camera()
//        Light light = new Light(new Vector3f(0, 0, -20), new Vector3f(1, 1, 1))
        Light light = new Light(new Vector3f(0, 20, 40), new Vector3f(1, 1, 1))



        List<Entity> entities = args.length > 1 ?
                Scenarios.scenarioFromName(args[1]) :
                Scenarios.dragons()
//                { throw new RuntimeException("No scenario specified.") }()

        mainGameLoop(camera, light, renderer, entities)
//        PerformanceTester.doPerformanceTest { gameLoop(camera, light, renderer, entities) }

        renderer.cleanup()
        loader.cleanup()
        DisplayManager.closeDisplay()
    }

    private static void mainGameLoop(Camera camera, Light light, MasterRenderer renderer, List<Entity> entities) {
        CommandRegistry.registerCommands()
        while (!Display.isCloseRequested()) {
            KeyboardManager.process()
            gameLoop(camera, light, renderer, entities)
        }
    }

    private static void gameLoop(Camera camera, Light light, MasterRenderer renderer, List<Entity> entities) {
        DisplayManager.prepare()
        camera.move(DisplayManager.delta)

        entities*.increaseRotation(0, (float) (60 * DisplayManager.delta), 0)
        entities.each { renderer.processEntity(it) }

        renderer.render(light, camera)
        DisplayManager.updateDisplay()
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
