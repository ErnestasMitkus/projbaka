package com.mist.algot.graphics.rendering

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

    private boolean wasPressedRenderFrustumKey = false
    private boolean shouldRenderFrustumPlanes = false
    private boolean wasPressedRecalcFrustum = false

    void render(Light sun, Camera camera) {
        def viewMatrix = camera.viewMatrix

        renderer.clear()
        boolean renderingFrustumPlanes = shouldRenderFrustum()
        if (renderingFrustumPlanes) {
            frustumShader.start()
            if (shouldRecalculateFrustum() || frustumPlanes.empty) {
                setupFrustumPlanes(camera)
                frustumShader.loadFrustumPlanes(frustumPlanes)
            }
            frustumShader.loadViewMatrix(viewMatrix)
            renderer.prepareForFrustum()
            renderer.renderFrustum()
            frustumShader.stop()
        }

        renderer.prepare(renderingFrustumPlanes)
        shader.start()
        shader.loadLight(sun)
        shader.loadViewMatrix(viewMatrix)
        shader.loadViewFrustumPlanes(FrustumHelpers.extractPlanes(frustumPlanes))
        renderer.render(entities)
        shader.stop()
        entities.clear()
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

    private boolean shouldRenderFrustum() {
        boolean keydown = Keyboard.isKeyDown(Keyboard.KEY_PERIOD)

        // none. pressed key first time = true
        // none. key hold/nohold = false
        // yes. pressed key first time = false
        // yes. key hold/nohold = yes

        if (!keydown) {
            wasPressedRenderFrustumKey = false
        } else if (!wasPressedRenderFrustumKey) {
            wasPressedRenderFrustumKey = true
            shouldRenderFrustumPlanes = !shouldRenderFrustumPlanes
            println "Frustum plane rendering ${shouldRenderFrustumPlanes ? "enabled." : "disabled"}"
        }
        return shouldRenderFrustumPlanes
    }

    private boolean shouldRecalculateFrustum() {
        boolean keydown = Keyboard.isKeyDown(Keyboard.KEY_LBRACKET)

        // wasPressed && !keydown -> FALSE, wasPressed = false
        // !wasPressed && !keydown -> FALSE, wasPressed = false
        // wasPressed && keyDown -> FALSE, wasPressed = true
        // !wasPressed && keyDown -> TRUE, wasPressed = true

        if (!keydown) {
            wasPressedRecalcFrustum = false
            return false
        } else if (!wasPressedRecalcFrustum) {
            wasPressedRecalcFrustum = true
            return true
        }
        return false
    }

    void cleanup() {
        shader.cleanup()
        frustumShader.cleanup()
    }
}
