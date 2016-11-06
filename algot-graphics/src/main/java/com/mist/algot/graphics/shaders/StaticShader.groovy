package com.mist.algot.graphics.shaders

import com.mist.algot.graphics.rendering.Loader
import org.lwjgl.util.vector.Matrix4f

class StaticShader extends ShaderProgram {

    private static final String VERTEX_FILE = "/shaders/main.vert"
    private static final String FRAGMENT_FILE = "/shaders/main.frag"

    private int location_transformationMatrix

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE)
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(Loader.VERTICES_ATTRIB_INDEX, "position")
        bindAttribute(Loader.TEXTURE_COORDINATES_ATTRIB_INDEX, "textureCoords")
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = getUniformLocation("transformationMatrix")
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        loadMatrix(location_transformationMatrix, matrix)
    }
}
