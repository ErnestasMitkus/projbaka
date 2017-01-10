package com.mist.algot.graphics.rendering

import com.mist.algot.graphics.entities.Entity
import com.mist.algot.graphics.hidden.Exchange
import com.mist.algot.graphics.hidden.ExchangeProperties
import com.mist.algot.graphics.models.TexturedModel
import com.mist.algot.graphics.shaders.StaticShader
import org.lwjgl.opengl.Display
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL13
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30
import org.lwjgl.util.vector.Matrix4f

import static com.mist.algot.graphics.hidden.ExchangeProperties.CAMERA
import static com.mist.algot.graphics.rendering.Loader.NORMALS_ATTRIB_INDEX
import static com.mist.algot.graphics.rendering.Loader.TEXTURE_COORDINATES_ATTRIB_INDEX
import static com.mist.algot.graphics.rendering.Loader.VERTICES_ATTRIB_INDEX
import static com.mist.algot.graphics.utils.ModelsHelpers.extractIndices
import static com.mist.algot.graphics.utils.ModelsHelpers.transformToPrimitiveIndices
import static com.mist.algot.graphics.utils.OpenGLHelpers.rebindIndices

class Renderer {

    private static final float FOV = 70; // field of view
    private static final float NEAR_PLANE = 0.1;
    private static final float FAR_PLANE = 1000;

    private final StaticShader staticShader
    private final Matrix4f projectionMatrix

    Renderer(StaticShader staticShader) {
        projectionMatrix = createProjectionMatrix()
        this.staticShader = staticShader
        staticShader.start()
        staticShader.loadProjectionMatrix(projectionMatrix)
        staticShader.stop()
    }

    static void prepare() {
        // back culling
//        GL11.glEnable(GL11.GL_CULL_FACE)
//        GL11.glCullFace(GL11.GL_BACK)

        // painters method (THIS OR Z BUFFER)
//        GL11.glDepthFunc(GL11.GL_NEVER);

        // z buffer method
        GL11.glEnable(GL11.GL_DEPTH_TEST)
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT)
        GL11.glClearColor(0.3f, 0, 0, 1)
    }

    void render(Map<TexturedModel, List<Entity>> entities) {
        entities.keySet().each { renderEntities(it, entities[it]) }
    }

    int len = 0

    private void renderEntities(TexturedModel model, List<Entity> entities) {
        withEnabledAttributes([VERTICES_ATTRIB_INDEX, TEXTURE_COORDINATES_ATTRIB_INDEX, NORMALS_ATTRIB_INDEX]) {
            entities.each { entity ->
                def texture = model.texture
                def rawModel = model.rawModel

                GL30.glBindVertexArray(rawModel.vaoId)
                if (len == 0) {
                    int[] indices = transformToPrimitiveIndices(extractIndices(rawModel.mesh.faces))
                    rebindIndices(rawModel.indicesVbo, indices)
                    len = indices.length
                }

                staticShader.loadTransformationMatrix(entity.transformationMatrix)
                staticShader.loadShineVariables(texture.shineDamper, texture.reflectivity)

                GL13.glActiveTexture(GL13.GL_TEXTURE0)
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.id)
                GL11.glDrawElements(GL11.GL_TRIANGLES, len, GL11.GL_UNSIGNED_INT, 0)
            }
        }
    }

    private static Matrix4f createProjectionMatrix() {
        float aspectRatio = (float) Display.displayMode.width / (float) Display.displayMode.height
        float xScale = (1f / Math.tan(Math.toRadians(FOV / 2f)))
        float yScale = xScale * aspectRatio
        float frustumLength = FAR_PLANE - NEAR_PLANE

        def projectionMatrix = new Matrix4f()
        projectionMatrix.m00 = xScale
        projectionMatrix.m11 = yScale
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustumLength)
        projectionMatrix.m23 = -1
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustumLength)
        projectionMatrix.m33 = 0

        return projectionMatrix
    }

    private static void withEnabledAttributes(List<Integer> attribs, Closure closure) {
        attribs.forEach(GL20.&glEnableVertexAttribArray)
        closure.call()
        attribs.forEach(GL20.&glDisableVertexAttribArray)
    }

}
