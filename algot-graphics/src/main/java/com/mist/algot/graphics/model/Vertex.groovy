package com.mist.algot.graphics.model

import org.lwjgl.util.vector.Vector3f

class Vertex extends Vector3f {

    Vertex(float x, float y, float z) {
        super(x, y, z)
    }

    Vertex(Vector3f vector) {
        super(vector.x, vector.y, vector.z)
    }
}
