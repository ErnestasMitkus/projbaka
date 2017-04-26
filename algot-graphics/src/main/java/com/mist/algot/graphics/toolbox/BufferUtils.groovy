package com.mist.algot.graphics.toolbox

import org.lwjgl.util.vector.Vector4f

import java.nio.FloatBuffer

import static org.lwjgl.BufferUtils.createFloatBuffer

class BufferUtils {

    static FloatBuffer loadToBuffer(List<Vector4f> vectors) {
        def buffer = createFloatBuffer(vectors.size() * 4)
        vectors.each {
            buffer.put(it.x)
            buffer.put(it.y)
            buffer.put(it.z)
            buffer.put(it.w)
        }
        buffer.flip()
        buffer
    }
}
