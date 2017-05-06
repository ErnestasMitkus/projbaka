package com.mist.algot.graphics.rendering

import com.mist.algot.graphics.entities.Camera
import com.mist.algot.graphics.entities.Entity
import com.mist.algot.graphics.entities.FrustumPlane
import com.mist.algot.graphics.entities.Light
import com.mist.algot.graphics.models.TexturedModel
import com.mist.algot.graphics.shaders.FrustumShader
import com.mist.algot.graphics.shaders.StaticShader
import com.mist.algot.graphics.toolbox.FrustumHelpers
import com.mist.algot.graphics.toolbox.Maths
import org.lwjgl.input.Keyboard
import org.lwjgl.util.vector.Matrix4f
import org.lwjgl.util.vector.Vector3f
import org.lwjgl.util.vector.Vector4f

import java.util.concurrent.atomic.AtomicInteger

import static com.mist.algot.graphics.toolbox.Maths.calculateFrustumPlanes
import static com.mist.algot.graphics.toolbox.Maths.calculateFrustumPlanesPoints

class MasterRenderer {

    private final StaticShader shader = new StaticShader()
    private final FrustumShader frustumShader = new FrustumShader()
    private final Renderer renderer = new Renderer(shader, frustumShader)

    private final Map<TexturedModel, List<Entity>> entities = [:]

    private List<FrustumPlane> frustumPlanes = []

    private long counter = 0
    private boolean wasPressedRenderFrustumKey = false
    private boolean shouldRenderFrustumPlanes = false
    private boolean wasPressedRecalcFrustum = false

    List<Vector3f> calcWorldPositions(Camera camera, List<Vector3f> points) {
//        def mat = Matrix4f.mul(renderer.projectionMatrix, camera.viewMatrix, null)  // projectionMatrix * viewMatrix * position
        def mat = renderer.projectionMatrix                                         // projectionMatrix * position

        points.collect {
            def vec4 = Maths.multiply(mat, new Vector4f(it.x, it.y, it.z, 1))
            new Vector3f(vec4.x, vec4.y, vec4.z)
        }
    }

    public void render(Light sun, Camera camera) {
        def viewMatrix = camera.viewMatrix
//        def frustumPlanes = calculateFrustumPlanes(renderer.projectionMatrix, viewMatrix)
//
//        if (counter++ == 3) {
//            println(calculateFrustumPlanesPoints(camera))
//        }

        renderer.clear()
        boolean renderingFrustumPlanes = shouldRenderFrustum()
        if (renderingFrustumPlanes) {
            frustumShader.start()
            if (shouldRecalculateFrustum() || frustumPlanes.empty) {
                println "Calculating frustum planes"
                setupFrustumPlanes(camera)
                frustumShader.loadFrustumPlanes(frustumPlanes)

            }
            frustumShader.loadViewMatrix(viewMatrix)
            renderer.prepareForFrustum()
            renderer.renderFrustum()
            frustumShader.stop()

            if (counter++ > 300 && Keyboard.isKeyDown(Keyboard.KEY_0)) {
                counter = 0
                def points = FrustumHelpers.extractPoints(frustumPlanes) // 4x each plane

//                println ""
//                println ""
//                println "Near: ${points[20..23]}, normal: ${frustumPlanes[5].normal}"
//                println "Left: ${points[4..7]}, normal: ${frustumPlanes[1].normal}"
            }

            if (counter++ > 300 && Keyboard.isKeyDown(Keyboard.KEY_9)) {
                counter = 0;
                println "Camera position: $camera.position"
            }

//            println "Camera: $camera.position,  rightPlaneTR: ${frustumPlanes[0].a}, transformedRightPlaneTR ${calcRightPlane(camera)}"
        }

        renderer.prepare(renderingFrustumPlanes)
        shader.start()
        shader.loadLight(sun)
        shader.loadViewMatrix(viewMatrix)
//        shader.loadViewFrustumPlanes(frustumPlanes)
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
        println planesPoints
        def frustumPlanes = FrustumHelpers.transformToPlanes(planesPoints)
        this.frustumPlanes = frustumPlanes
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

    public void cleanup() {
        shader.cleanup()
        frustumShader.cleanup()
    }

}
