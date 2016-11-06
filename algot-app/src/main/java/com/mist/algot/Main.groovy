package com.mist.algot

import com.mist.algot.graphics.entities.Camera
import com.mist.algot.graphics.entities.Entity
import com.mist.algot.graphics.entities.Light
import com.mist.algot.graphics.models.TexturedModel
import com.mist.algot.graphics.rendering.DisplayManager
import com.mist.algot.graphics.rendering.Loader
import com.mist.algot.graphics.models.RawModel
import com.mist.algot.graphics.rendering.Renderer
import com.mist.algot.graphics.shaders.StaticShader
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
        StaticShader shader = new StaticShader()
        Renderer renderer = new Renderer(shader)

        float[] vertices = [
				-0.5f,0.5f,-0.5f,
				-0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,-0.5f,
				0.5f,0.5f,-0.5f,

				-0.5f,0.5f,0.5f,
				-0.5f,-0.5f,0.5f,
				0.5f,-0.5f,0.5f,
				0.5f,0.5f,0.5f,

				0.5f,0.5f,-0.5f,
				0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,0.5f,
				0.5f,0.5f,0.5f,

				-0.5f,0.5f,-0.5f,
				-0.5f,-0.5f,-0.5f,
				-0.5f,-0.5f,0.5f,
				-0.5f,0.5f,0.5f,

				-0.5f,0.5f,0.5f,
				-0.5f,0.5f,-0.5f,
				0.5f,0.5f,-0.5f,
				0.5f,0.5f,0.5f,

				-0.5f,-0.5f,0.5f,
				-0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,0.5f
		];

		float[] textureCoords = [
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0
		];

		int[] indices = [
            0,1,3,
            3,1,2,
            4,5,7,
            7,5,6,
            8,9,11,
            11,9,10,
            12,13,15,
            15,13,14,
            16,17,19,
            19,17,18,
            20,21,23,
            23,21,22
		];

//        RawModel squareModel = loader.loadToVAO(vertices, textureCoords, indices)
        RawModel squareModel = OBJLoader.loadObjModel("/objects/dragon.obj", loader)
        ModelTexture texture = new ModelTexture(loader.loadTexture("/textures/white.png"))
        texture.shineDamper = 10
        texture.reflectivity = 1
        TexturedModel texturedModel = new TexturedModel(squareModel, texture)

        Entity entity = new Entity(texturedModel, new Vector3f(0, 0, -25))
        Light light = new Light(new Vector3f(0, 0, -20), new Vector3f(1, 1, 1))

        Camera camera = new Camera()

        while (!Display.isCloseRequested()) {
            renderer.prepare()

            entity.increaseRotation(0, 1, 0)
            camera.move()

            shader.start()
            shader.loadLight(light)
            shader.loadViewMatrix(camera)
            renderer.render(entity)
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
