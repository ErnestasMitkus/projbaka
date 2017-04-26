package com.mist.algot.graphics.entities

import com.mist.algot.graphics.toolbox.Maths
import org.lwjgl.input.Keyboard
import org.lwjgl.util.vector.Matrix4f
import org.lwjgl.util.vector.Vector3f

class Camera {

    private static final float CAM_SPEED = 10f
    private static final float PYR_SPEED = 20f

    private Vector3f position = new Vector3f()
    private float pitch // how high or low the camera is aimed
    private float yaw // how much left or right camera is aiming
    private float roll // how much the camera is tilted

    Camera() {}

    void move(float delta) {
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            position.z -= CAM_SPEED * delta
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            position.z += CAM_SPEED * delta
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            position.x += CAM_SPEED * delta
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            position.x -= CAM_SPEED * delta
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            position.y += CAM_SPEED * delta
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
            position.y -= CAM_SPEED * delta
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
            pitch -= PYR_SPEED * delta
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_2)) {
            pitch += PYR_SPEED * delta
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_3)) {
            yaw -= PYR_SPEED * delta
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_4)) {
            yaw += PYR_SPEED * delta
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_5)) {
            roll -= PYR_SPEED * delta
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_6)) {
            roll += PYR_SPEED * delta
        }
    }

    Matrix4f getViewMatrix() {
        Maths.createViewMatrix(position, pitch, yaw, roll)
    }

    Vector3f getPosition() {
        return position
    }

    float getPitch() {
        return pitch
    }

    float getYaw() {
        return yaw
    }

    float getRoll() {
        return roll
    }
}
