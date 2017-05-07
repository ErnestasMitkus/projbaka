package com.mist.algot.graphics.entities

import org.lwjgl.util.vector.Vector3f
import org.newdawn.slick.Color

import static com.mist.algot.graphics.toolbox.Vectors.minus

class FrustumPlane {

    private final Vector3f a, b, c, d
    private final Vector3f normal
    private final Color color

    /**
    * @description Vectors a, b, c, d must be specified in the counter-clockwise order
     * and a-c, b-d are the opposite vertexes of the QUAD
    *
    * */
    FrustumPlane(Vector3f a, Vector3f b, Vector3f c, Vector3f d, Color color) {
        this.a = a
        this.b = b
        this.c = c
        this.d = d
        this.color = color

        def dir = Vector3f.cross(minus(b, a), minus(c, a), null)
        this.normal = dir.normalise(null)
    }

    FrustumPlane(Vector3f a, Vector3f b, Vector3f c, Vector3f d) {
        this(a, b, c, d, Color.white)
    }

    Vector3f getA() {
        return a
    }

    Vector3f getB() {
        return b
    }

    Vector3f getC() {
        return c
    }

    Vector3f getD() {
        return d
    }

    List<Vector3f> getPoints() {
        [a, b, c, d]
    }

    List<Vector3f> getPlane1() {
        [a, b, c]
    }

    List<Vector3f> getPlane2() {
        [c, d, a]
    }

    Vector3f getNormal() {
        return normal
    }

    Color getColor() {
        return color
    }
}
