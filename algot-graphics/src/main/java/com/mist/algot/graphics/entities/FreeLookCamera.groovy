package com.mist.algot.graphics.entities

import com.mist.algot.graphics.toolbox.Maths
import org.lwjgl.input.Keyboard
import org.lwjgl.input.Mouse
import org.lwjgl.util.vector.Matrix3f
import org.lwjgl.util.vector.Matrix4f
import org.lwjgl.util.vector.Vector3f
import org.lwjgl.util.vector.Vector4f

import static com.mist.algot.graphics.rendering.DisplayManager.getPerformanceManager

class FreeLookCamera implements Camera {

    private static final float MOVEMENT_SPEED = 0.05f * 60
    private static final float ROTATIONAL_SPEED = 0.002f * 60
    private static final Vector3f UP = new Vector3f(0, 1f, 0)

    private Vector3f position = new Vector3f()
    private Vector3f viewDirection = new Vector3f(0, 0, -1f)

    private Vector3f strafeDirection

    @Override
    void move() {
        updateMouse()
        strafeDirection = Vector3f.cross(viewDirection, UP, null)
        updateMovement()
    }

    private void updateMovement() {
        float delta = performanceManager.delta
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            moveForward((float)(MOVEMENT_SPEED * delta))
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            moveForward((float) (-MOVEMENT_SPEED * delta))
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            moveRight((float)(MOVEMENT_SPEED * delta))
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            moveRight((float)(-MOVEMENT_SPEED * delta))
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            moveUp((float)(MOVEMENT_SPEED * delta))
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
            moveUp((float)(-MOVEMENT_SPEED * delta))
        }
    }

    private void moveForward(float amount) {
        Vector3f vecForward = Maths.mul(viewDirection, amount)
        position = Maths.add(position, vecForward)
    }

    private void moveRight(float amount) {
        Vector3f vecRight = Maths.mul(strafeDirection, amount)
        position = Maths.add(position, vecRight)
    }

    private void moveUp(float amount) {
        Vector3f vecUp = Maths.mul(UP, amount)
        position = Maths.add(position, vecUp)
    }

    private void updateMouse() {
        if (!Mouse.isButtonDown(1)) {
            return
        }
        int dx = Mouse.getDX()
        int dy = Mouse.getDY()
        float delta = performanceManager.delta

        Vector3f toRotateAround = Vector3f.cross(viewDirection, UP, null)
        Matrix4f rotator = new Matrix4f().rotate((float)(-dx * ROTATIONAL_SPEED * delta), UP)
                                         .rotate((float)(dy * ROTATIONAL_SPEED * delta), toRotateAround)
        Matrix3f rotatorReduced = Maths.reduce(rotator)
        viewDirection = Maths.multiply(rotatorReduced, viewDirection)
    }

    @Override
    Matrix4f getViewMatrix() {
        Maths.lookAt(position, Vector3f.add(position, viewDirection, null), UP)
    }
}
