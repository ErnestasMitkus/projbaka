package com.mist.algot.graphics.rendering

import com.mist.algot.graphics.entities.Camera
import com.mist.algot.graphics.model.Face
import com.mist.algot.graphics.model.Vertex
import com.mist.algot.graphics.toolbox.Maths
import org.lwjgl.opengl.Display
import org.lwjgl.opengl.GL11
import org.lwjgl.util.vector.Matrix4f
import org.lwjgl.util.vector.Vector3f
import org.lwjgl.util.vector.Vector4f
import org.newdawn.slick.Color

class FlatRenderer {

    private static final float FOV = 70; // field of view
    private static final float NEAR_PLANE = 0.1;
    private static final float FAR_PLANE = 1000;

//    private final StaticShader staticShader
//    private final Matrix4f projectionMatrix

    private boolean enabledCulling
    private boolean enabledDepthTest

    private Color backgroundColor = Color.black

    private Matrix4f transformationMatrix = new Matrix4f()
    private Matrix4f viewMatrix = new Matrix4f()
    private final Matrix4f projectionMatrix

    FlatRenderer() {
        setColor(Color.white)
        projectionMatrix = createProjectionMatrix()
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

    void renderTriangles(List<Face> triangles) {
        GL11.glBegin(GL11.GL_TRIANGLES)
        triangles.each {
            drawVertex(it.first)
            drawVertex(it.second)
            drawVertex(it.third)
        }
        GL11.glEnd()
    }

    void renderLines(List<Face> triangles) {
        enableWireframe()
        renderTriangles(triangles)
        disableWireframe()
    }

    void loadTexture(int textureId) {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
    }

    void renderTextured(List<Face> triangles) {
        GL11.glBegin(GL11.GL_TRIANGLES)

        triangles.each {
            drawTexturedVertex(it.first)
            drawTexturedVertex(it.second)
            drawTexturedVertex(it.third)
        }

        GL11.glEnd()
    }

    void drawVertex(Vertex vertex) {
        if (vertex.textured) {
            GL11.glTexCoord2f(vertex.textureCoords.x, vertex.textureCoords.y)
        }
        def vec = new Vector4f(vertex.position.x, vertex.position.y, vertex.position.z, 1f)
        def transformed = applyTransformations(vec)
        float x = transformed.x,
              y = transformed.y,
              z = transformed.z
        GL11.glVertex3f(x, y, z)
    }

    Vector3f applyTransformations(Vector4f position) {
        def worldPosition = Maths.multiply(transformationMatrix, position)
        def projView = Matrix4f.mul(projectionMatrix, viewMatrix, null)

        Maths.normalize(Maths.multiply(projView, worldPosition))
    }

    void enableWireframe() {
        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE)
    }

    void disableWireframe() {
        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL)
    }

    void updateTransformationMatrix(Matrix4f transformationMatrix) {
        this.transformationMatrix = transformationMatrix
    }

    void updateViewMatrix(Camera camera) {
        this.viewMatrix = camera.viewMatrix
    }

    static void setColor(Color color) {
        GL11.glColor3f(color.r, color.g, color.b)
    }

    Color getBackgroundColor() {
        return backgroundColor
    }

    void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor
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
}
