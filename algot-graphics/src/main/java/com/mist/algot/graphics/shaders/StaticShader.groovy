package com.mist.algot.graphics.shaders

import com.mist.algot.graphics.entities.Camera
import com.mist.algot.graphics.rendering.Loader
import org.lwjgl.util.vector.Matrix4f

class StaticShader extends ShaderProgram {

    private static final String VERTEX_FILE = "/shaders/main.vert"
    private static final String FRAGMENT_FILE = "/shaders/main.frag"

    private int location_transformationMatrix
    private int location_projectionMatrix
    private int location_viewMatrix

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
        location_projectionMatrix = getUniformLocation("projectionMatrix")
        location_viewMatrix = getUniformLocation("viewMatrix")
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        loadMatrix(location_transformationMatrix, matrix)
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        loadMatrix(location_projectionMatrix, matrix)
    }

    public void loadViewMatrix(Camera camera) {
        loadMatrix(location_viewMatrix, camera.viewMatrix)
    }
}
