package com.mist.algot.graphics.toolbox

import org.lwjgl.util.vector.Matrix4f
import org.lwjgl.util.vector.Vector3f

class Maths {

    private static final Vector3f AXIS_X = new Vector3f(1, 0, 0)
    private static final Vector3f AXIS_Y = new Vector3f(0, 1, 0)
    private static final Vector3f AXIS_Z = new Vector3f(0, 0, 1)

    private Maths() {}

    public static Matrix4f createTransformationMatrix(Vector3f translation, Vector3f rotation, float scale) {
        createTransformationMatrix(translation, rotation, new Vector3f(scale, scale, scale))
    }

    public static Matrix4f createTransformationMatrix(Vector3f translation, Vector3f rotation, Vector3f scale) {
        Matrix4f matrix = new Matrix4f()
        matrix.setIdentity()
        Matrix4f.translate(translation, matrix, matrix)
        rotate(matrix, rotation)
        Matrix4f.scale(scale, matrix, matrix)
        return matrix
    }

    public static Matrix4f createViewMatrix(Vector3f position, float pitch, float yaw, float roll) {
        Matrix4f viewMatrix = new Matrix4f()
        rotate(viewMatrix, new Vector3f(pitch, yaw, roll))
        Matrix4f.translate(invert(position), viewMatrix, viewMatrix)
        return viewMatrix
    }

    public static void rotate(Matrix4f matrix, Vector3f rotation) {
        Matrix4f.rotate((float) Math.toRadians(rotation.x), AXIS_X, matrix, matrix)
        Matrix4f.rotate((float) Math.toRadians(rotation.y), AXIS_Y, matrix, matrix)
        Matrix4f.rotate((float) Math.toRadians(rotation.z), AXIS_Z, matrix, matrix)
    }

    public static Vector3f invert(Vector3f vector) {
        new Vector3f(-vector.x, -vector.y, -vector.z)
    }

}
