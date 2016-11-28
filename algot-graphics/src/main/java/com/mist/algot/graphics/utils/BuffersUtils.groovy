package com.mist.algot.graphics.utils

import org.lwjgl.BufferUtils

import java.nio.FloatBuffer
import java.nio.IntBuffer

class BuffersUtils {

    static IntBuffer storeDataInIntBuffer(int[] data) {
        def buffer = BufferUtils.createIntBuffer(data.length)
        buffer.put(data)
        buffer.flip()
        buffer
    }

    static FloatBuffer storeDataInFloatBuffer(float[] data) {
        def buffer = BufferUtils.createFloatBuffer(data.length)
        buffer.put(data)
        buffer.flip()
        buffer
    }

}
