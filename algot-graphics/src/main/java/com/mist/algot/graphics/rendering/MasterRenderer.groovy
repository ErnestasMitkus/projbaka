package com.mist.algot.graphics.rendering

import com.mist.algot.graphics.entities.LockedCamera
import com.mist.algot.graphics.entities.Entity
import com.mist.algot.graphics.entities.Light
import com.mist.algot.graphics.hidden.Exchange
import com.mist.algot.graphics.models.TexturedModel
import com.mist.algot.graphics.shaders.StaticShader

import static com.mist.algot.graphics.hidden.ExchangeProperties.CAMERA

class MasterRenderer {

    private final StaticShader shader = new StaticShader()
    private final Renderer renderer = new Renderer(shader)

    private final Map<TexturedModel, List<Entity>> entities = [:]

    public void render(Light sun, LockedCamera camera) {
        renderer.prepare()

        Exchange.fromEntitiesMap(entities)
            .withProperty(CAMERA, camera)

        shader.start()
        shader.loadLight(sun)
        shader.loadViewMatrix(camera)
        renderer.render(entities)
        shader.stop()
        entities.clear()
    }

    void processEntity(Entity entity) {
        def entityModel = entity.model
        List<Entity> batch = entities[entityModel]
        if (!batch) {
            batch = []
            entities[entityModel] = batch
        }
        batch.add(entity)
    }

    public void cleanup() {
        shader.cleanup()
    }

}
