package com.mist.algot.graphics.rendering

import com.mist.algot.graphics.entities.Camera
import com.mist.algot.graphics.entities.Entity
import com.mist.algot.graphics.entities.Light
import com.mist.algot.graphics.models.TexturedModel
import com.mist.algot.graphics.shaders.StaticShader

import static com.mist.algot.graphics.toolbox.Maths.calculateFrustumPlanes

class MasterRenderer {

    private final StaticShader shader = new StaticShader()
    private final Renderer renderer = new Renderer(shader)

    private final Map<TexturedModel, List<Entity>> entities = [:]

    public void render(Light sun, Camera camera) {
        def viewMatrix = camera.viewMatrix
        def frustumPlanes = calculateFrustumPlanes(renderer.projectionMatrix, viewMatrix)

        renderer.prepare()
        shader.start()
        shader.loadLight(sun)
        shader.loadViewMatrix(viewMatrix)
        shader.loadViewFrustumPlanes(frustumPlanes)
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

    public void cleanup() {
        shader.cleanup()
    }

}
