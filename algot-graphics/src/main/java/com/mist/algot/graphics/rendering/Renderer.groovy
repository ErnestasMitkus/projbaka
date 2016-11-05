package com.mist.algot.graphics.rendering

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30

import static com.mist.algot.graphics.rendering.Loader.VERTICES_ATTRIB_INDEX

class Renderer {

    void prepare() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT)
        GL11.glClearColor(1, 0, 0, 1)
    }

    void render(RawModel model) {
        GL30.glBindVertexArray(model.vaoId)
        GL20.glEnableVertexAttribArray(VERTICES_ATTRIB_INDEX)
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.vertexCount, GL11.GL_UNSIGNED_INT, 0)
        GL20.glDisableVertexAttribArray(0)
        GL30.glBindVertexArray(0)
    }

}
