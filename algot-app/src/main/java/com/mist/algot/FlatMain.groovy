package com.mist.algot

import com.mist.algot.graphics.entities.Camera
import com.mist.algot.graphics.entities.FreeLookCamera
import com.mist.algot.graphics.entities.LockedCamera
import com.mist.algot.graphics.rendering.DisplayManager
import com.mist.algot.graphics.rendering.FlatRenderer
import com.mist.algot.temp.CubeObj
import com.mist.algot.temp.ObjRenderer
import org.lwjgl.opengl.Display
import org.lwjgl.util.vector.Matrix4f
import org.newdawn.slick.Color

import static com.mist.algot.util.OsHelpers.determineNativesPath

class FlatMain {

    public static void main(String[] args) {
        System.setProperty("org.lwjgl.librarypath", determineNativesPath())

        DisplayManager.createDisplay(2, 1)

        def obj = new CubeObj("/textures/doge.png")
//        def obj = new TestObj("/textures/doge.png")
//        def obj = new LineObj()

        def flatRenderer = new FlatRenderer()
        def renderer = new ObjRenderer(flatRenderer)

        def transformation = new Matrix4f()
        flatRenderer.updateTransformationMatrix(transformation)

        Camera camera = new FreeLookCamera()


        while (!Display.isCloseRequested()) {
            flatRenderer.prepare()
            camera.move()
            flatRenderer.updateViewMatrix(camera)
//            transformation.translate(new Vector3f(0, 0, -0.01f))
//            transformation.rotate(0.2f, new Vector3f(1, 1, 0))

            flatRenderer.setColor(Color.white)
//            renderer.render(obj)
            renderer.renderWireframe(obj)
//            renderer.renderTextured(obj)

            DisplayManager.updateDisplay()
        }


//        Loader loader = new Loader()
//
//        RawModel squareModel = OBJLoader.loadObjModel("/objects/dragon.obj", loader)
//        ModelTexture texture = new ModelTexture(loader.loadTexture("/textures/white.png"))
//        texture.shineDamper = 10
//        texture.reflectivity = 1
//        TexturedModel texturedModel = new TexturedModel(squareModel, texture)
//
//        Entity entity = new Entity(texturedModel, new Vector3f(0, 0, -25))
//        Light light = new Light(new Vector3f(0, 0, -20), new Vector3f(1, 1, 1))
//
//        Camera camera = new Camera()
//
//        MasterRenderer renderer = new MasterRenderer()
//
//        while (!Display.isCloseRequested()) {
//            entity.increaseRotation(0, 1, 0)
//            camera.move()
//
//            renderer.processEntity(entity)
//
//            renderer.render(light, camera)
//            DisplayManager.updateDisplay()
//        }
//
//        renderer.cleanup()
//        loader.cleanup()

        DisplayManager.closeDisplay()
    }

}
