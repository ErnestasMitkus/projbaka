package com.mist.algot.graphics.rendering

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30

import java.nio.FloatBuffer
import java.nio.IntBuffer

class Loader {

    private static final int VERTEX_COUNT = 3
    private static final int DISTANCE_BETWEEN_VERTICES = 0 // have you got any data between them

    public static final int VERTICES_ATTRIB_INDEX = 0

    private final List<Integer> vaos = []
    private final List<Integer> vbos = []

    RawModel loadToVAO(float[] positions, int[] indices) {
        int vaoId = createVAO()
        bindIndicesBuffer(indices)
        storeDataInAttributeList(VERTICES_ATTRIB_INDEX, positions)
        unbindVAO()
        return new RawModel(vaoId, indices.length)
    }

    void cleanup() {
        vaos.each { GL30.glDeleteVertexArrays(it) }
        vaos.clear()

        vbos.each { GL15.glDeleteBuffers(it) }
        vbos.clear()
    }

    private int createVAO() {
        int vaoId = GL30.glGenVertexArrays()
        vaos.add(vaoId)
        GL30.glBindVertexArray(vaoId)
        vaoId
    }

    private void storeDataInAttributeList(int attributeNumber, float[] data) {
        int vboId = GL15.glGenBuffers()
        vbos.add(vboId)
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId)

        def buffer = storeDataInFloatBuffer(data)
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW)

        int offset = 0 // start from the beggining of data
        GL20.glVertexAttribPointer(attributeNumber, VERTEX_COUNT, GL11.GL_FLOAT, false, DISTANCE_BETWEEN_VERTICES, offset)

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)
    }

    private static void unbindVAO() {
        GL30.glBindVertexArray(0)
    }

    private void bindIndicesBuffer(int[] indices) {
        int vboId = GL15.glGenBuffers()
        vbos.add(vboId)
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboId)

        def buffer = storeDataInIntBuffer(indices)
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW)
    }

    private static IntBuffer storeDataInIntBuffer(int[] data) {
        def buffer = BufferUtils.createIntBuffer(data.length)
        buffer.put(data)
        buffer.flip()
        buffer
    }

    private static FloatBuffer storeDataInFloatBuffer(float[] data) {
        def buffer = BufferUtils.createFloatBuffer(data.length)
        buffer.put(data)
        buffer.flip()
        buffer
    }

}
