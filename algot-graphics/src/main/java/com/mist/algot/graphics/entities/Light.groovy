package com.mist.algot.graphics.entities

import org.lwjgl.util.vector.Vector3f

class Light {

    private Vector3f position
    private Vector3f color

    Light(Vector3f position, Vector3f color) {
        this.position = position
        this.color = color
    }

    Vector3f getPosition() {
        return position
    }

    Vector3f getColor() {
        return color
    }
}
