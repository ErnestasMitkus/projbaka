package com.mist.algot.graphics.shaders

import com.mist.algot.graphics.entities.Light
import com.mist.algot.graphics.rendering.Loader
import org.lwjgl.util.vector.Matrix4f
import org.lwjgl.util.vector.Vector4f

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
    private int location_frustumPlanes
    private int location_useFrustumCulling

    public StaticShader() {
//        super(VERTEX_FILE, GEOMETRY_FILE, FRAGMENT_FILE)
        super(VERTEX_FILE, FRAGMENT_FILE)
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
        location_frustumPlanes = getUniformLocation("frustumPlanes")
        location_useFrustumCulling = getUniformLocation("useFrustumCulling")
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        loadMatrix(location_transformationMatrix, matrix)
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        loadMatrix(location_projectionMatrix, matrix)
    }

    public void loadViewMatrix(Matrix4f viewMatrix) {
        loadMatrix(location_viewMatrix, viewMatrix)
    }

    void loadViewFrustumPlanes(List<Vector4f> frustumPlanes) {
        loadVectorArray(location_frustumPlanes, frustumPlanes)
    }

    void setUseFrustumCulling(boolean useFrustumCulling) {
        loadBoolean(location_useFrustumCulling, useFrustumCulling)
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
