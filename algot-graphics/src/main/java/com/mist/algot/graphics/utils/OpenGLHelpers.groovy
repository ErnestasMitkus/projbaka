package com.mist.algot.graphics.utils

import com.mist.algot.graphics.model.Indice
import org.lwjgl.opengl.GL15

import static com.mist.algot.graphics.utils.BuffersUtils.storeDataInIntBuffer
import static com.mist.algot.graphics.utils.ModelsHelpers.transformToPrimitiveIndices

class OpenGLHelpers {

    static void rebindIndices(int vboId, List<Indice> indices) {
        rebindIndices(vboId, transformToPrimitiveIndices(indices))
    }

    static void rebindIndices(int vboId, int[] indices) {
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboId)
        def buffer = storeDataInIntBuffer(indices)
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_DYNAMIC_DRAW)
    }

}
