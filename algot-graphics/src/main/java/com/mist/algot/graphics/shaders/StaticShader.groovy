package com.mist.algot.graphics.shaders

import com.mist.algot.graphics.entities.Camera
import com.mist.algot.graphics.entities.Light
import com.mist.algot.graphics.rendering.Loader
import org.lwjgl.util.vector.Matrix4f

class StaticShader extends ShaderProgram {

    private static final String VERTEX_FILE = "/shaders/main.vert"
    private static final String GEOMETRY_FILE = "/shaders/main.geom"
    private static final String FRAGMENT_FILE = "/shaders/main.frag"

    private int location_transformationMatrix
    private int location_projectionMatrix
    private int location_viewMatrix
    private int location_lightPosition
    private int location_lightColor
    private int location_shineDamper
    private int location_reflectivity

    public StaticShader() {
        super(VERTEX_FILE, GEOMETRY_FILE, FRAGMENT_FILE)
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(Loader.VERTICES_ATTRIB_INDEX, "position")
        bindAttribute(Loader.TEXTURE_COORDINATES_ATTRIB_INDEX, "textureCoords")
        bindAttribute(Loader.NORMALS_ATTRIB_INDEX, "normal")
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = getUniformLocation("transformationMatrix")
        location_projectionMatrix = getUniformLocation("projectionMatrix")
        location_viewMatrix = getUniformLocation("viewMatrix")
        location_lightPosition = getUniformLocation("lightPosition")
        location_lightColor = getUniformLocation("lightColor")
        location_shineDamper = getUniformLocation("shineDamper")
        location_reflectivity = getUniformLocation("reflectivity")
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

    public void loadLight(Light light) {
        loadVector(location_lightPosition, light.position)
        loadVector(location_lightColor, light.color)
    }

    public void loadShineVariables(float shineDamper, float reflectivity) {
        loadFloat(location_shineDamper, shineDamper)
        loadFloat(location_reflectivity, reflectivity)
    }
}
