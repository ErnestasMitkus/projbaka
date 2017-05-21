package com.mist.algot.graphics.rendering

import com.mist.algot.graphics.controls.KeyboardManager
import com.mist.algot.graphics.entities.Camera
import com.mist.algot.graphics.entities.Entity
import com.mist.algot.graphics.entities.FrustumPlane
import com.mist.algot.graphics.entities.Light
import com.mist.algot.graphics.models.TexturedModel
import com.mist.algot.graphics.shaders.FrustumShader
import com.mist.algot.graphics.shaders.StaticShader
import com.mist.algot.graphics.toolbox.FrustumHelpers
import org.lwjgl.input.Keyboard
import org.lwjgl.util.vector.Matrix4f
import org.lwjgl.util.vector.Vector3f
import org.lwjgl.util.vector.Vector4f

import static com.mist.algot.graphics.toolbox.Maths.calculateFrustumPlanesPoints

class MasterRenderer {

    private final StaticShader shader = new StaticShader()
    private final FrustumShader frustumShader = new FrustumShader()
    private final Renderer renderer = new Renderer(shader, frustumShader)

    private final Map<TexturedModel, List<Entity>> entities = [:]

    private List<FrustumPlane> frustumPlanes = []
    private boolean frustumPlanesInvalidated = true
    private List<Vector4f> frustPlanes = []

    private boolean frustumPlanesRenderingEnabled
    private boolean frustumPlanesRenderingInvalidated
    private boolean frustumPlanesRecalculatingPending
    private boolean lockFrustumToCamera
    private boolean useFrustumCulling
    private Matrix4f lastCameraViewMatrix = null

    MasterRenderer() {
        KeyboardManager.onPress(Keyboard.KEY_PERIOD, {
            frustumPlanesRenderingEnabled = !frustumPlanesRenderingEnabled
        })
        KeyboardManager.register(Keyboard.KEY_LBRACKET, {
            if (it == KeyboardManager.EventType.HOLD) {
                frustumPlanesRecalculatingPending = true
            }
        })
        KeyboardManager.onPress(Keyboard.KEY_L, {
            lockFrustumToCamera = !lockFrustumToCamera
        })
    }

    void render(Light sun, Camera camera) {
        def viewMatrix = camera.viewMatrix

        renderer.clear()
        updateFrustumPreferences()
        processFrustumPlanes(camera)

        if (frustumPlanesInvalidated) {
            frustumPlanesInvalidated = false
            frustPlanes = FrustumHelpers.extractPlanes(frustumPlanes)
        }

        renderer.prepare(frustumPlanesRenderingEnabled)
        shader.start()
        shader.loadLight(sun)
        shader.loadViewMatrix(viewMatrix)
        shader.loadViewFrustumPlanes(frustPlanes)
        renderer.render(filteredEntities())
        shader.stop()
        entities.clear()
    }

    private Map<TexturedModel, List<Entity>> filteredEntities() {
        if (!HidingManager.frustumCullingEnabled) {
            return entities
        }
        entities.collectEntries { key, value ->
            def boundingSphere = key.boundingSphere
            def v = value.findAll {
                boundingSphere.isInsideFrustum(it.position, it.scale, frustPlanes)
            }
            [(key): v]
        } as Map<TexturedModel, List<Entity>>
    }

    private void updateFrustumPreferences() {
        if (useFrustumCulling != HidingManager.frustumCullingEnabled) {
            useFrustumCulling = HidingManager.frustumCullingEnabled
            shader.start()
            shader.setUseFrustumCulling(useFrustumCulling)
            shader.stop()
        }
    }

    void processFrustumPlanes(Camera camera) {
        frustumShader.start()
        if (lockFrustumToCamera && camera.viewMatrix != lastCameraViewMatrix) {
            lastCameraViewMatrix = camera.viewMatrix
            frustumPlanesRecalculatingPending = true
        }
        if (frustumPlanesRecalculatingPending || !frustumPlanes) {
            frustumPlanesRecalculatingPending = false
            setupFrustumPlanes(camera)
        }

        if (frustumPlanesRenderingEnabled) {
            if (frustumPlanesRenderingInvalidated) {
                frustumPlanesRenderingInvalidated = false
                frustumShader.loadFrustumPlanes(frustumPlanes)
            }
            frustumShader.loadViewMatrix(camera.viewMatrix)
            renderer.prepareForFrustum()
            renderer.renderFrustum()
        }
        frustumShader.stop()
    }

    void processEntity(Entity entity) {
        def entityModel = entity.model
        List<Entity> batch = entities[entityModel]
        if (!batch) {
            batch = []
            entities.put(entityModel, batch)
        }
        batch.add(entity)
    }

    void setupFrustumPlanes(Camera camera) {
        def planesPoints = calculateFrustumPlanesPoints(camera)
        this.frustumPlanes = FrustumHelpers.transformToPlanes(planesPoints)
        frustumPlanesInvalidated = true
        frustumPlanesRenderingInvalidated = true
    }

    void cleanup() {
        shader.cleanup()
        frustumShader.cleanup()
    }
}
