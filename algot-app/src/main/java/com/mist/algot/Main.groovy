package com.mist.algot

import com.mist.algot.graphics.entities.Camera
import com.mist.algot.graphics.entities.Entity
import com.mist.algot.graphics.entities.Light
import com.mist.algot.graphics.models.RawModel
import com.mist.algot.graphics.models.TexturedModel
import com.mist.algot.graphics.rendering.DisplayManager
import com.mist.algot.graphics.rendering.Loader
import com.mist.algot.graphics.rendering.MasterRenderer
import com.mist.algot.graphics.textures.ModelTexture
import com.mist.algot.graphics.utils.OBJLoader
import org.apache.commons.lang3.SystemUtils
import org.lwjgl.opengl.Display
import org.lwjgl.util.vector.Vector3f

class Main {

    public static void main(String[] args) {
        System.setProperty("org.lwjgl.librarypath", determineNativesPath())

        DisplayManager.createDisplay()

        Loader loader = new Loader()

        RawModel squareModel = OBJLoader.loadObjModel("/objects/simpleCube.obj", loader)
        ModelTexture texture = new ModelTexture(loader.loadTexture("/textures/doge.png"))
        texture.shineDamper = 10
        texture.reflectivity = 1
        TexturedModel texturedModel = new TexturedModel(squareModel, texture)

        Entity entity = new Entity(texturedModel, new Vector3f(0, 0, -25))
        Light light = new Light(new Vector3f(0, 0, -20), new Vector3f(1, 1, 1))

        Camera camera = new Camera()

        MasterRenderer renderer = new MasterRenderer()

        while (!Display.isCloseRequested()) {
            DisplayManager.prepare()
            entity.increaseRotation(0, (float) (60 * DisplayManager.delta), 0)
            camera.move(DisplayManager.delta)

            renderer.processEntity(entity)

            renderer.render(light, camera)
            DisplayManager.updateDisplay()
        }

        renderer.cleanup()
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
