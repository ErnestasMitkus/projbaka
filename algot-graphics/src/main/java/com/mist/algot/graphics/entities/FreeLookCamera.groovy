package com.mist.algot.graphics.entities

import com.mist.algot.graphics.toolbox.Maths
import org.lwjgl.input.Keyboard
import org.lwjgl.input.Mouse
import org.lwjgl.util.vector.Matrix3f
import org.lwjgl.util.vector.Matrix4f
import org.lwjgl.util.vector.Vector3f
import org.lwjgl.util.vector.Vector4f

class FreeLookCamera implements Camera {

    private static final float MOVEMENT_SPEED = 0.05f
    private static final float ROTATIONAL_SPEED = 0.002f
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
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            moveForward(MOVEMENT_SPEED)
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            moveForward(-MOVEMENT_SPEED)
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            moveRight(MOVEMENT_SPEED)
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            moveRight(-MOVEMENT_SPEED)
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            moveUp(MOVEMENT_SPEED)
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
            moveUp(-MOVEMENT_SPEED)
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

        Vector3f toRotateAround = Vector3f.cross(viewDirection, UP, null)
        Matrix4f rotator = new Matrix4f().rotate((float)(-dx * ROTATIONAL_SPEED), UP)
                                         .rotate((float)(dy * ROTATIONAL_SPEED), toRotateAround)
        Matrix3f rotatorReduced = Maths.reduce(rotator)
        viewDirection = Maths.multiply(rotatorReduced, viewDirection)
    }

    @Override
    Matrix4f getViewMatrix() {
        Maths.lookAt(position, Vector3f.add(position, viewDirection, null), UP)
    }
}
