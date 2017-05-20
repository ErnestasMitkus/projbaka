package com.mist.algot.graphics.shaders

import com.mist.algot.graphics.utils.FileUtils
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL32
import org.lwjgl.util.vector.Matrix4f
import org.lwjgl.util.vector.Vector3f
import org.lwjgl.util.vector.Vector4f

import java.nio.FloatBuffer

import static com.mist.algot.graphics.toolbox.BufferUtils.loadToBuffer
import static com.mist.algot.graphics.utils.FailureUtils.doSilent

abstract class ShaderProgram {

    private int programId
    private int vertexShaderId
    private int geometryShaderId
    private int fragmentShaderId

    private final boolean usingGeometryShader

    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(4*4)

    public ShaderProgram(String vertexFile, String geometryFile, String fragmentFile) {
        usingGeometryShader = geometryFile != null && !geometryFile.empty

        vertexShaderId = loadShader(vertexFile, GL20.GL_VERTEX_SHADER)
        if (usingGeometryShader) {
            geometryShaderId = loadShader(geometryFile, GL32.GL_GEOMETRY_SHADER)
        }
        fragmentShaderId = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER)
        programId = GL20.glCreateProgram()

        GL20.glAttachShader(programId, vertexShaderId)
        if (usingGeometryShader) {
            GL20.glAttachShader(programId, geometryShaderId)
        }
        GL20.glAttachShader(programId, fragmentShaderId)
        bindAttributes()
        GL20.glLinkProgram(programId)
        GL20.glValidateProgram(programId)
        getAllUniformLocations()
    }

    protected abstract void getAllUniformLocations()

    protected int getUniformLocation(String uniformName) {
        GL20.glGetUniformLocation(programId, uniformName)
    }

    void start() {
        GL20.glUseProgram(programId)
    }

    void stop() {
        GL20.glUseProgram(0)
    }

    void cleanup() {
        stop()
        doSilent { GL20.glDetachShader(programId, vertexShaderId) }
        doSilent { GL20.glDetachShader(programId, fragmentShaderId) }
        doSilent { GL20.glDeleteShader(vertexShaderId) }
        doSilent { GL20.glDeleteShader(fragmentShaderId) }
        if (usingGeometryShader) {
            doSilent { GL20.glDetachShader(programId, geometryShaderId) }
            doSilent { GL20.glDeleteShader(geometryShaderId) }
        }
        doSilent { GL20.glDeleteProgram(programId) }
    }

    protected abstract void bindAttributes()

    protected void bindAttribute(int attribute, String variableName) {
        GL20.glBindAttribLocation(programId, attribute, variableName)
    }

    protected void loadFloat(int location, float value) {
        GL20.glUniform1f(location, value)
    }

    protected void loadVector(int location, Vector3f vector) {
        GL20.glUniform3f(location, vector.x, vector.y, vector.z)
    }

    protected void loadVectorArray(int location, List<Vector4f> vectors) {
        GL20.glUniform4(location, loadToBuffer(vectors))
    }

    protected void loadBoolean(int location, boolean value) {
        loadFloat(location, value ? 1 : 0)
    }

    protected void loadMatrix(int location, Matrix4f matrix4f) {
        matrix4f.store(matrixBuffer)
        matrixBuffer.flip()
        GL20.glUniformMatrix4(location, false, matrixBuffer)
    }

    private static int loadShader(String file, int type){
        String shaderSource = FileUtils.loadFile(file).text

        int shaderId = GL20.glCreateShader(type)
        GL20.glShaderSource(shaderId, shaderSource)
        GL20.glCompileShader(shaderId)

        if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println(GL20.glGetShaderInfoLog(shaderId, 500))
            throw new RuntimeException("Could not compile ${shaderTypeToString(type)} shader($file)")
        }

        return shaderId
    }

    private static String shaderTypeToString(int type) {
        switch(type) {
            case GL20.GL_VERTEX_SHADER:
                return "vertex"
            case GL32.GL_GEOMETRY_SHADER:
                return "geometry"
            case GL20.GL_FRAGMENT_SHADER:
                return "fragment"
            default:
                return "unknown(type=$type)"
        }
    }

}
