package com.mist.algot.graphics.entities

import org.lwjgl.util.vector.Matrix4f

interface Camera {
    void move()
    Matrix4f getViewMatrix()
}
