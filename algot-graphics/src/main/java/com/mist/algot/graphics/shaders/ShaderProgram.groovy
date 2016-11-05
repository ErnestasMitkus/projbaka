package com.mist.algot.graphics.shaders

import com.mist.algot.graphics.FileUtils
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20

abstract class ShaderProgram {

    private int programId
    private int vertexShaderId
    private int fragmentShaderId

    public ShaderProgram(String vertexFile, String fragmentFile) {
        vertexShaderId = loadShader(vertexFile, GL20.GL_VERTEX_SHADER)
        fragmentShaderId = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER)
        programId = GL20.glCreateProgram()

        GL20.glAttachShader(programId, vertexShaderId)
        GL20.glAttachShader(programId, fragmentShaderId)
        bindAttributes()
        GL20.glLinkProgram(programId)
        GL20.glValidateProgram(programId)
    }

    void start() {
        GL20.glUseProgram(programId)
    }

    void stop() {
        GL20.glUseProgram(0)
    }

    void cleanup() {
        stop()
        GL20.glDetachShader(programId, vertexShaderId)
        GL20.glDetachShader(programId, fragmentShaderId)
        GL20.glDeleteShader(vertexShaderId)
        GL20.glDeleteShader(fragmentShaderId)
        GL20.glDeleteProgram(programId)
    }

    protected abstract void bindAttributes()

    protected void bindAttribute(int attribute, String variableName) {
        GL20.glBindAttribLocation(programId, attribute, variableName)
    }

    private static int loadShader(String file, int type){
        String shaderSource = FileUtils.loadFile(file).text

        int shaderId = GL20.glCreateShader(type)
        GL20.glShaderSource(shaderId, shaderSource)
        GL20.glCompileShader(shaderId)

        if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println(GL20.glGetShaderInfoLog(shaderId, 500))
            throw new RuntimeException("Could not compile shader")
        }

        return shaderId
    }

}
