package com.mist.algot.graphics.models

import com.mist.algot.graphics.controls.KeyboardManager
import com.mist.algot.graphics.toolbox.Maths
import com.mist.algot.graphics.toolbox.Vectors
import org.lwjgl.input.Keyboard
import org.lwjgl.util.vector.Vector3f
import org.lwjgl.util.vector.Vector4f

class BoundingSphere {

    private final float sphereRadius
    private final Vector3f sphereOffset

    private static float TEST_RADIUS = 0.2f

    private static float offRadSpeed = 0.05f
    private static float offSpeed = 0.5f
    private static float TEST_OFFSET_X = 0
    private static float TEST_OFFSET_Y = 0
    private static float TEST_OFFSET_Z = 0
    private static Vector3f TEST_OFFSET = new Vector3f(0, 0, 0)

    static {
        KeyboardManager.onPress(Keyboard.KEY_0, {
            TEST_RADIUS += offRadSpeed
            println "New radius: $TEST_RADIUS"
        })
        KeyboardManager.onPress(Keyboard.KEY_9, {
            TEST_RADIUS -= offRadSpeed
            println "New radius: $TEST_RADIUS"
        })
        KeyboardManager.onPress(Keyboard.KEY_NUMPAD7)   { TEST_OFFSET_X -= offSpeed; recalc() }
        KeyboardManager.onPress(Keyboard.KEY_NUMPAD8)   { TEST_OFFSET_Z -= offSpeed; recalc() }
        KeyboardManager.onPress(Keyboard.KEY_NUMPAD9)   { TEST_OFFSET_X += offSpeed; recalc() }
        KeyboardManager.onPress(Keyboard.KEY_NUMPAD5)   { TEST_OFFSET_Z += offSpeed; recalc() }
        KeyboardManager.onPress(Keyboard.KEY_ADD)       { TEST_OFFSET_Y -= offSpeed; recalc() }
        KeyboardManager.onPress(Keyboard.KEY_SUBTRACT)  { TEST_OFFSET_Y += offSpeed; recalc() }
        recalc()
    }

    private static void recalc() {
        TEST_OFFSET = new Vector3f(TEST_OFFSET_X, TEST_OFFSET_Y, TEST_OFFSET_Z)
        println "New offset: $TEST_OFFSET"
    }

    BoundingSphere(float sphereRadius, Vector3f sphereOffset = new Vector3f(0, 0, 0)) {
        this.sphereRadius = sphereRadius
        this.sphereOffset = sphereOffset
    }

    boolean isInsideFrustum(Vector3f position, Vector3f scale, List<Vector4f> frustumPlanes) {
        if (sphereRadius < 0) {
            return true
        }
        float maxScale = Math.max(Math.max(scale.x, scale.y), scale.z)

        Maths.sphereInsideFrustum(Vectors.plus(position, TEST_OFFSET), TEST_RADIUS * maxScale as float, frustumPlanes)
//        Maths.sphereInsideFrustum(Vectors.plus(position, TEST_OFFSET), TEST_RADIUS * maxScale as float, frustumPlanes)
    }

    float getSphereRadius() {
        sphereRadius
    }

    Vector3f getSphereOffset() {
        sphereOffset
    }
}
