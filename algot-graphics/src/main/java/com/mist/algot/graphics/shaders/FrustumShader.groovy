package com.mist.algot.graphics.shaders

import com.mist.algot.graphics.entities.FrustumPlane
import com.mist.algot.graphics.toolbox.FrustumHelpers
import com.mist.algot.graphics.toolbox.Utils
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30
import org.lwjgl.util.vector.Matrix4f
import org.newdawn.slick.Color

import static com.mist.algot.graphics.toolbox.BufferUtils.storeDataInFloatBuffer
import static com.mist.algot.graphics.toolbox.BufferUtils.storeDataInIntBuffer
import static com.mist.algot.graphics.toolbox.Utils.expand
import static com.mist.algot.graphics.toolbox.Vectors.toArray

class FrustumShader extends ShaderProgram {

    private static final String VERTEX_FILE = "/shaders/frustum.vert"
    private static final String FRAGMENT_FILE = "/shaders/frustum.frag"

    private int location_projectionMatrix
    private int location_viewMatrix

    static final int VERTICES_ATTRIB_INDEX = 0
    static final int NORMALS_ATTRIB_INDEX = 1
    static final int COLORS_ATTRIB_INDEX = 2

    private Integer vaoId
    private Integer vertVboId, normVboId, indVboId, colsVboId

    FrustumShader() {
        super(VERTEX_FILE, null, FRAGMENT_FILE)
    }

    @Override
    protected void getAllUniformLocations() {
        location_projectionMatrix = getUniformLocation("projectionMatrix")
        location_viewMatrix = getUniformLocation("viewMatrix")
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(VERTICES_ATTRIB_INDEX, "position")
        bindAttribute(NORMALS_ATTRIB_INDEX, "normal")
        bindAttribute(COLORS_ATTRIB_INDEX, "color")
    }

    void loadProjectionMatrix(Matrix4f matrix) {
        loadMatrix(location_projectionMatrix, matrix)
    }

    void loadViewMatrix(Matrix4f viewMatrix) {
        loadMatrix(location_viewMatrix, viewMatrix)
    }

    void loadFrustumPlanes(List<FrustumPlane> frustumPlanes) {
        // position  D
        // normal  D
        // indices S
        // color  S

        // right left bottom top far near



        def positions = FrustumHelpers.extractPoints(frustumPlanes) // 4x each plane
        def normals = expand(frustumPlanes.normal, 4)

        if (!vaoId) {
            def indices = FrustumHelpers.extractIndices(frustumPlanes)
            def colors = expand(frustumPlanes.color, 4)

            createVAO()
            bindIndicesBuffer(indices)
            bindColorsBuffer(colors)
        }
        bindVAO()

        vertVboId = storeDataInAttributeList(VERTICES_ATTRIB_INDEX, 3, toArray(positions))
        normVboId = storeDataInAttributeList(NORMALS_ATTRIB_INDEX, 3, toArray(normals))
        unbindVAO()
    }

    void bindIndicesBuffer(List<Integer> indices) {
        int[] indicesArray = indices.toArray() as int[]
        indVboId = GL15.glGenBuffers()
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indVboId)
        def buffer = storeDataInIntBuffer(indicesArray)
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW)
    }

    void bindColorsBuffer(List<Color> colors) {
        float[] colorsArray = new float[colors.size() * 4]
        int index = 0
        colors.each {
            colorsArray[index++] = it.r
            colorsArray[index++] = it.g
            colorsArray[index++] = it.b
            colorsArray[index++] = it.a
        }

        colsVboId = storeDataInAttributeList(COLORS_ATTRIB_INDEX, 4, colorsArray, GL15.GL_DYNAMIC_DRAW)
    }

    int storeDataInAttributeList(int attributeNumber, int attributeSize, float[] data, int usage = GL15.GL_STATIC_DRAW) {
        int vboId = GL15.glGenBuffers()
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId)

        def buffer = storeDataInFloatBuffer(data)
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, usage)

        int offset = 0 // start from the beggining of data
        GL20.glVertexAttribPointer(attributeNumber, attributeSize, GL11.GL_FLOAT, false, 0, offset)

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)
        return vboId
    }

    Integer getVaoId() {
        return vaoId
    }

    void cleanup() {
        GL30.glDeleteVertexArrays(vaoId)
        [vertVboId, normVboId, indVboId, colsVboId].each {
            GL15.glDeleteBuffers(it)
        }
        vaoId = null
        vertVboId = null
        normVboId = null
        indVboId = null
        colsVboId = null
    }

    private void createVAO() {
        this.vaoId = GL30.glGenVertexArrays()
        bindVAO()
    }

    private void bindVAO() {
        GL30.glBindVertexArray(vaoId)
    }

    private void unbindVAO() {
        GL30.glBindVertexArray(0)
    }
}
