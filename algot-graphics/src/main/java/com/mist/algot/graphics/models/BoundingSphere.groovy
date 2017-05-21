package com.mist.algot.graphics.models

import com.mist.algot.graphics.toolbox.Maths
import com.mist.algot.graphics.toolbox.Vectors
import org.lwjgl.util.vector.Vector3f
import org.lwjgl.util.vector.Vector4f

class BoundingSphere {

    private final float sphereRadius
    private final Vector3f sphereOffset

    BoundingSphere(float sphereRadius, Vector3f sphereOffset = new Vector3f(0, 0, 0)) {
        this.sphereRadius = sphereRadius
        this.sphereOffset = sphereOffset
    }

    boolean isInsideFrustum(Vector3f position, Vector3f scale, List<Vector4f> frustumPlanes) {
        if (sphereRadius < 0) {
            return true
        }
        float maxScale = Math.max(Math.max(scale.x, scale.y), scale.z)

        Maths.sphereInsideFrustum(Vectors.plus(position, sphereOffset), sphereRadius * maxScale as float, frustumPlanes)
    }

    float getSphereRadius() {
        sphereRadius
    }

    Vector3f getSphereOffset() {
        sphereOffset
    }
}
