package com.mist.algot.graphics.entities

import com.mist.algot.graphics.toolbox.Maths
import com.mist.algot.graphics.toolbox.Vectors
import org.lwjgl.input.Keyboard
import org.lwjgl.input.Mouse
import org.lwjgl.util.vector.Matrix4f
import org.lwjgl.util.vector.Vector3f
import org.lwjgl.util.vector.Vector4f

class Camera {

    private static final float CAM_SPEED = 13f
    private static final float PITCH_SPEED = 13f
    private static final float YAW_SPEED = 13f
    private static final float PYR_SPEED = 20f

    private Vector3f position = new Vector3f()
    private float pitch // how high or low the camera is aimed
    private float yaw // how much left or right camera is aiming
    private float roll // how much the camera is tilted

    private boolean controlViewWithMouse = false

    Camera() {}

    void move(float delta) {
        checkForViewControlsUpdate()
        if (controlViewWithMouse) {
            moveViewWithMouse(delta)
        } else {
            moveViewWithKeyboard(delta)
        }
        movePosition(delta)
    }

    private void movePosition(float delta) {
        Vector3f moveVec = new Vector3f()
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            moveVec.z -= CAM_SPEED * delta
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            moveVec.z += CAM_SPEED * delta
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            moveVec.x += CAM_SPEED * delta
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            moveVec.x -= CAM_SPEED * delta
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            moveVec.y += CAM_SPEED * delta
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
            moveVec.y -= CAM_SPEED * delta
        }

        if (moveVec.lengthSquared() > 0) {
            use(Vectors) {
                def translationForward = viewDirection * moveVec.z
                def translationRight = right * moveVec.x
                def translationUp = up * moveVec.y

                position = position + translationForward.negate(null) + translationRight + translationUp
            }
        }
    }

    private void moveViewWithKeyboard(float delta) {
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

    private void moveViewWithMouse(float delta) {
        pitch -= Mouse.DY * PITCH_SPEED * delta
        yaw += Mouse.DX * YAW_SPEED * delta
    }

    private void checkForViewControlsUpdate() {
        controlViewWithMouse = Mouse.isButtonDown(0)
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

    Vector3f getViewDirection() {
//        new Vector3f(
//            vectorLength * sin(pitch) * cos(yaw) as float,
//            vectorLength * sin(pitch) * sin(yaw) as float,
//            vectorLength * cos(pitch) as float
//        )
        def vec4 = Maths.multiply(viewMatrix, new Vector4f(0, 0, -1, 0))
        new Vector3f(vec4.x, vec4.y, vec4.z)
    }

    Vector3f getUp() {
        def vec4 = Maths.multiply(viewMatrix, new Vector4f(0, 1, 0, 0))
        new Vector3f(vec4.x, vec4.y, vec4.z)
    }

    Vector3f getRight() {
        def vec4 = Maths.multiply(viewMatrix, new Vector4f(1, 0, 0, 0))
        new Vector3f(vec4.x, vec4.y, vec4.z)
    }
}
