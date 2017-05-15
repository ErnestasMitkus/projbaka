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

import static com.mist.algot.graphics.toolbox.Maths.calculateFrustumPlanesPoints

class MasterRenderer {

    private final StaticShader shader = new StaticShader()
    private final FrustumShader frustumShader = new FrustumShader()
    private final Renderer renderer = new Renderer(shader, frustumShader)

    private final Map<TexturedModel, List<Entity>> entities = [:]

    private List<FrustumPlane> frustumPlanes = []

    private boolean frustumPlanesRenderingEnabled = false
    private boolean frustumPlanesRecalculatingPending = false

    MasterRenderer() {
        KeyboardManager.register(Keyboard.KEY_PERIOD, {
            if (it == KeyboardManager.EventType.PRESSED) {
                frustumPlanesRenderingEnabled = !frustumPlanesRenderingEnabled
            }
        })
        KeyboardManager.register(Keyboard.KEY_LBRACKET, {
            if (it == KeyboardManager.EventType.PRESSED) {
                frustumPlanesRecalculatingPending = true
            }
        })
    }

    void render(Light sun, Camera camera) {
        def viewMatrix = camera.viewMatrix

        renderer.clear()
        processFrustumPlanes(camera)

        renderer.prepare(frustumPlanesRenderingEnabled)
        shader.start()
        shader.loadLight(sun)
        shader.loadViewMatrix(viewMatrix)
        shader.loadViewFrustumPlanes(FrustumHelpers.extractPlanes(frustumPlanes))
        renderer.render(entities)
        shader.stop()
        entities.clear()
    }

    void processFrustumPlanes(Camera camera) {
        frustumShader.start()
        if (frustumPlanesRecalculatingPending || !frustumPlanes) {
            frustumPlanesRecalculatingPending = false
            setupFrustumPlanes(camera)
            frustumShader.loadFrustumPlanes(frustumPlanes)
        }

        if (frustumPlanesRenderingEnabled) {
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
    }

    void cleanup() {
        shader.cleanup()
        frustumShader.cleanup()
    }
}
