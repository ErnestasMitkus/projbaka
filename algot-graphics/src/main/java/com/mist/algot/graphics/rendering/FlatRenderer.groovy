package com.mist.algot.graphics.rendering

import com.mist.algot.graphics.entities.Entity
import com.mist.algot.graphics.model.Vertex
import com.mist.algot.graphics.models.TexturedModel
import com.mist.algot.graphics.shaders.StaticShader
import org.lwjgl.opengl.Display
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL13
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30
import org.lwjgl.util.vector.Matrix4f
import org.lwjgl.util.vector.Vector3f
import org.newdawn.slick.Color

import static com.mist.algot.graphics.rendering.Loader.NORMALS_ATTRIB_INDEX
import static com.mist.algot.graphics.rendering.Loader.TEXTURE_COORDINATES_ATTRIB_INDEX
import static com.mist.algot.graphics.rendering.Loader.VERTICES_ATTRIB_INDEX

class FlatRenderer {

    private static final float FOV = 70; // field of view
    private static final float NEAR_PLANE = 0.1;
    private static final float FAR_PLANE = 1000;

//    private final StaticShader staticShader
//    private final Matrix4f projectionMatrix

    private boolean enabledCulling
    private boolean enabledDepthTest

    private Color backgroundColor = Color.black

    FlatRenderer() {
        setColor(Color.white)
//        projectionMatrix = createProjectionMatrix()
//        this.staticShader = staticShader
//        staticShader.start()
//        staticShader.loadProjectionMatrix(projectionMatrix)
//        staticShader.stop()
    }

    void prepare() {
        int mask = GL11.GL_COLOR_BUFFER_BIT
        mask |= enabledDepthTest ? GL11.GL_DEPTH_BUFFER_BIT : 0
        GL11.glClear(mask)
        GL11.glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a)
    }

    void enableCulling() {
        enabledCulling = true
        GL11.glEnable(GL11.GL_CULL_FACE)
        GL11.glCullFace(GL11.GL_BACK)
    }

    void disableCulling() {
        enabledCulling = false
        GL11.glDisable(GL11.GL_CULL_FACE)
    }

    void enableDepthTest() {
        enabledDepthTest = true
        GL11.glEnable(GL11.GL_DEPTH_TEST)
        GL11.glDepthFunc(GL11.GL_LESS)
    }

    void disableDepthTest() {
        enabledDepthTest = false
        GL11.glDisable(GL11.GL_DEPTH_TEST)
    }

    void render(List<Vertex> vertices) {
        vertices.each {
            GL11.glVertex3f(it.x, it.y, it.z)
        }
    }

    static void setColor(Color color) {
        GL11.glColor3f(color.r, color.g, color.b)
    }

//    private void renderEntities(TexturedModel model, List<Entity> entities) {
//        withEnabledAttributes([VERTICES_ATTRIB_INDEX, TEXTURE_COORDINATES_ATTRIB_INDEX, NORMALS_ATTRIB_INDEX]) {
//            entities.each { entity ->
//                def texture = model.texture
//                def rawModel = model.rawModel
//
//                GL30.glBindVertexArray(rawModel.vaoId)
//
//                staticShader.loadTransformationMatrix(entity.transformationMatrix)
//                staticShader.loadShineVariables(texture.shineDamper, texture.reflectivity)
//
//                GL13.glActiveTexture(GL13.GL_TEXTURE0)
//                GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.id)
//                GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.vertexCount, GL11.GL_UNSIGNED_INT, 0)
//            }
//        }
//    }

//    private static Matrix4f createProjectionMatrix() {
//        float aspectRatio = (float) Display.displayMode.width / (float) Display.displayMode.height
//        float xScale = (1f / Math.tan(Math.toRadians(FOV / 2f)))
//        float yScale = xScale * aspectRatio
//        float frustumLength = FAR_PLANE - NEAR_PLANE
//
//        def projectionMatrix = new Matrix4f()
//        projectionMatrix.m00 = xScale
//        projectionMatrix.m11 = yScale
//        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustumLength)
//        projectionMatrix.m23 = -1
//        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustumLength)
//        projectionMatrix.m33 = 0
//
//        return projectionMatrix
//    }

//    private static void withEnabledAttributes(List<Integer> attribs, Closure closure) {
//        attribs.forEach(GL20.&glEnableVertexAttribArray)
//        closure.call()
//        attribs.forEach(GL20.&glDisableVertexAttribArray)
//    }

    Color getBackgroundColor() {
        return backgroundColor
    }

    void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor
    }
}
