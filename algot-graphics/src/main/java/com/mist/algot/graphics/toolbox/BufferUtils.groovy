package com.mist.algot.graphics.toolbox

import org.lwjgl.util.vector.Vector4f

import java.nio.FloatBuffer
import java.nio.IntBuffer

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

    static IntBuffer storeDataInIntBuffer(int[] data) {
        def buffer = org.lwjgl.BufferUtils.createIntBuffer(data.length)
        buffer.put(data)
        buffer.flip()
        buffer
    }

    static FloatBuffer storeDataInFloatBuffer(float[] data) {
        def buffer = createFloatBuffer(data.length)
        buffer.put(data)
        buffer.flip()
        buffer
    }
}
