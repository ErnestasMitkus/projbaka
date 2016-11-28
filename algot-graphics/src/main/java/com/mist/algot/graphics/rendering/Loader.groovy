package com.mist.algot.graphics.rendering

import com.mist.algot.graphics.utils.FileUtils
import com.mist.algot.graphics.models.RawModel
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30
import org.newdawn.slick.opengl.Texture
import org.newdawn.slick.opengl.TextureLoader

import java.nio.FloatBuffer
import java.nio.IntBuffer

import static com.mist.algot.graphics.utils.BuffersUtils.storeDataInFloatBuffer
import static com.mist.algot.graphics.utils.BuffersUtils.storeDataInIntBuffer

class Loader {

    private static final int VECTOR_2_SIZE = 2
    private static final int VECTOR_3_SIZE = 3
    private static final int DISTANCE_BETWEEN_VERTICES = 0 // have you got any data between them

    public static final Integer VERTICES_ATTRIB_INDEX = 0
    public static final Integer TEXTURE_COORDINATES_ATTRIB_INDEX = 1
    public static final Integer NORMALS_ATTRIB_INDEX = 2

    private final List<Integer> vaos = []
    private final List<Integer> vbos = []
    private final List<Integer> textures = []

//    RawModel loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indices) {
//        int vaoId = createVAO()
//        bindIndicesBuffer(indices)
//        storeDataInAttributeList(VERTICES_ATTRIB_INDEX, VECTOR_3_SIZE, positions)
//        storeDataInAttributeList(TEXTURE_COORDINATES_ATTRIB_INDEX, VECTOR_2_SIZE, textureCoords)
//        storeDataInAttributeList(NORMALS_ATTRIB_INDEX, VECTOR_3_SIZE, normals)
//        unbindVAO()
//        return new RawModel(vaoId, indices)
//    }

    RawModel loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indices) {
        int vaoId = createVAO()

        int indicesVbo = GL15.glGenBuffers()
        vbos.add(indicesVbo)
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesVbo)

        storeDataInAttributeList(VERTICES_ATTRIB_INDEX, VECTOR_3_SIZE, positions)
        storeDataInAttributeList(TEXTURE_COORDINATES_ATTRIB_INDEX, VECTOR_2_SIZE, textureCoords)
        storeDataInAttributeList(NORMALS_ATTRIB_INDEX, VECTOR_3_SIZE, normals)
        unbindVAO()
        return new RawModel(vaoId, indicesVbo, indices)
    }

    public int loadTexture(String fileName) {
        Texture texture
        texture = TextureLoader.getTexture("PNG", FileUtils.loadFile(fileName))
        int textureId = texture.textureID
        textures.add(textureId)
        textureId
    }

    void cleanup() {
        vaos.forEach(GL30.&glDeleteVertexArrays)
        vbos.forEach(GL15.&glDeleteBuffers)
        textures.forEach(GL11.&glDeleteTextures)

        vaos.clear()
        vbos.clear()
        textures.clear()
    }

    private int createVAO() {
        int vaoId = GL30.glGenVertexArrays()
        vaos.add(vaoId)
        GL30.glBindVertexArray(vaoId)
        vaoId
    }

    private void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) {
        int vboId = GL15.glGenBuffers()
        vbos.add(vboId)
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId)

        def buffer = storeDataInFloatBuffer(data)
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW)

        int offset = 0 // start from the beggining of data
        GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, DISTANCE_BETWEEN_VERTICES, offset)

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)
    }

    private static void unbindVAO() {
        GL30.glBindVertexArray(0)
    }

    private int bindIndicesBuffer(int[] indices) {
        int vboId = GL15.glGenBuffers()
        vbos.add(vboId)
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboId)

        def buffer = storeDataInIntBuffer(indices)
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW)
        return vboId
    }



}
