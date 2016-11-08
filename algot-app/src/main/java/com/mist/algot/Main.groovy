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

import static com.mist.algot.util.OsHelpers.determineNativesPath

class Main {

    public static void main(String[] args) {
        System.setProperty("org.lwjgl.librarypath", determineNativesPath())

        DisplayManager.createDisplay()

        Loader loader = new Loader()

        RawModel squareModel = OBJLoader.loadObjModel("/objects/dragon.obj", loader)
        ModelTexture texture = new ModelTexture(loader.loadTexture("/textures/white.png"))
        texture.shineDamper = 10
        texture.reflectivity = 1
        TexturedModel texturedModel = new TexturedModel(squareModel, texture)

        Entity entity = new Entity(texturedModel, new Vector3f(0, 0, -25))
        Light light = new Light(new Vector3f(0, 0, -20), new Vector3f(1, 1, 1))

        Camera camera = new Camera()

        MasterRenderer renderer = new MasterRenderer()

        while (!Display.isCloseRequested()) {
            entity.increaseRotation(0, 1, 0)
            camera.move()

            renderer.processEntity(entity)

            renderer.render(light, camera)
            DisplayManager.updateDisplay()
        }

        renderer.cleanup()
        loader.cleanup()
        DisplayManager.closeDisplay()
    }

}
