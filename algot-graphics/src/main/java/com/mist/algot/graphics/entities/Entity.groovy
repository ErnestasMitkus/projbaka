package com.mist.algot.graphics.entities

import com.mist.algot.graphics.models.TexturedModel
import com.mist.algot.graphics.toolbox.Maths
import org.lwjgl.util.vector.Matrix4f
import org.lwjgl.util.vector.Vector3f

class Entity {

    private TexturedModel model
    private Vector3f position
    private Vector3f rotation
    private Vector3f scale

    Entity(TexturedModel model, Vector3f position) {
        this(model, position, new Vector3f(0, 0, 0), new Vector3f(1, 1, 1))
    }

    Entity(TexturedModel model, Vector3f position, Vector3f rotation, Vector3f scale) {
        this.model = model
        this.position = position
        this.rotation = rotation
        this.scale = scale
    }

    public void increasePosition(Vector3f pos) {
        increasePosition(pos.x, pos.y, pos.z)
    }

    public void increasePosition(float dx, float dy, float dz) {
        position.x += dx
        position.y += dy
        position.z += dz
    }

    public void increaseRotation(Vector3f pos) {
        increaseRotation(pos.x, pos.y, pos.z)
    }

    public void increaseRotation(float dx, float dy, float dz) {
        rotation.x += dx
        rotation.y += dy
        rotation.z += dz
    }

    TexturedModel getModel() {
        return model
    }

    Vector3f getPosition() {
        return position
    }

    Vector3f getRotation() {
        return rotation
    }

    Vector3f getScale() {
        return scale
    }

    Matrix4f getTransformationMatrix() {
        Maths.createTransformationMatrix(position, rotation, scale)
    }
}
