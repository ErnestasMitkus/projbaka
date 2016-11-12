package com.mist.algot.temp

import com.mist.algot.graphics.model.TexturedVertex
import com.mist.algot.graphics.model.VTriangle
import com.mist.algot.graphics.rendering.FlatRenderer
import org.lwjgl.opengl.GL11

import static com.mist.algot.temp.ObjHelpers.calculateTriangles

class ObjRenderer {

    private final FlatRenderer renderer

    ObjRenderer(FlatRenderer renderer) {
        this.renderer = renderer
    }

    void render(RenderableObj renderableObj) {
        List<TexturedVertex> vertices = ObjHelpers.enrichVertices(renderableObj.vertices, renderableObj.textureCoords)
        List<VTriangle> triangles = calculateTriangles(vertices, renderableObj.indices)
        renderer.renderTriangles(triangles)
    }

    void renderWireframe(RenderableObj renderableObj) {
        List<TexturedVertex> vertices = ObjHelpers.enrichVertices(renderableObj.vertices, renderableObj.textureCoords)
        List<VTriangle> triangles = calculateTriangles(vertices, renderableObj.indices)
        renderer.renderLines(triangles)
    }

    void renderTextured(RenderableObj renderableObj) {
        GL11.glEnable(GL11.GL_DEPTH_TEST)
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT)
        List<TexturedVertex> vertices = ObjHelpers.enrichVertices(renderableObj.vertices, renderableObj.textureCoords)
        List<VTriangle> triangles = calculateTriangles(vertices, renderableObj.indices)
        renderer.loadTexture(renderableObj.textureId)
        renderer.renderTextured(triangles)
        GL11.glDisable(GL11.GL_DEPTH_TEST)
    }

}
