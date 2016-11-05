package com.mist.algot.graphics.rendering

import com.mist.algot.graphics.models.RawModel
import com.mist.algot.graphics.models.TexturedModel
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL13
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30

import static com.mist.algot.graphics.rendering.Loader.TEXTURE_COORDINATES_ATTRIB_INDEX
import static com.mist.algot.graphics.rendering.Loader.VERTICES_ATTRIB_INDEX

class Renderer {

    void prepare() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT)
        GL11.glClearColor(1, 0, 0, 1)
    }

    void render(TexturedModel texturedModel) {
        RawModel model = texturedModel.rawModel
        GL30.glBindVertexArray(model.vaoId)
        withEnabledAttributes([VERTICES_ATTRIB_INDEX, TEXTURE_COORDINATES_ATTRIB_INDEX]) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0)
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.texture.id)
            GL11.glDrawElements(GL11.GL_TRIANGLES, model.vertexCount, GL11.GL_UNSIGNED_INT, 0)
        }
        GL30.glBindVertexArray(0)
    }

    private static void withEnabledAttributes(List<Integer> attribs, Closure closure) {
        attribs.forEach(GL20.&glEnableVertexAttribArray)
        closure.call()
        attribs.forEach(GL20.&glDisableVertexAttribArray)
    }

}
