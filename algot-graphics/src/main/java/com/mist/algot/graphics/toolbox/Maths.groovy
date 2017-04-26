package com.mist.algot.graphics.toolbox

import org.lwjgl.util.vector.Matrix4f
import org.lwjgl.util.vector.Vector3f
import org.lwjgl.util.vector.Vector4f

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

    static void normalize(Vector4f vector) {
//        vector.normalise(vector)


        def vec3 = new Vector3f(vector.x, vector.y, vector.z)
        vec3.normalise(vec3)

        vector.x = vec3.x
        vector.y = vec3.y
        vector.z = vec3.z
        vector
    }

    static List<Vector4f> calculateFrustumPlanes(Matrix4f projectionMatrix, Matrix4f viewMatrix) {
        // Taken from example in http://www.crownandcutlass.com/features/technicaldetails/frustum.html
        def clip = Matrix4f.mul(viewMatrix, projectionMatrix, null)

        // Calculate right plane
        def rightPlane = new Vector4f()
        rightPlane.x = clip.m03 - clip.m00
        rightPlane.y = clip.m13 - clip.m10
        rightPlane.z = clip.m23 - clip.m20
        rightPlane.w = clip.m33 - clip.m30
        normalize(rightPlane)

        // Calculate left plane
        def leftPlane = new Vector4f()
        leftPlane.x = clip.m03 + clip.m00
        leftPlane.y = clip.m13 + clip.m10
        leftPlane.z = clip.m23 + clip.m20
        leftPlane.w = clip.m33 + clip.m30
        normalize(leftPlane)

        // Calculate bottom plane
        def botPlane = new Vector4f()
        botPlane.x = clip.m03 + clip.m01
        botPlane.y = clip.m13 + clip.m11
        botPlane.z = clip.m23 + clip.m21
        botPlane.w = clip.m33 + clip.m31
        normalize(botPlane)

        // Calculate top plane
        def topPlane = new Vector4f()
        topPlane.x = clip.m03 - clip.m01
        topPlane.y = clip.m13 - clip.m11
        topPlane.z = clip.m23 - clip.m21
        topPlane.w = clip.m33 - clip.m31
        normalize(topPlane)

        // Calculate far plane
        def farPlane = new Vector4f()
        farPlane.x = clip.m03 - clip.m02
        farPlane.y = clip.m13 - clip.m12
        farPlane.z = clip.m23 - clip.m22
        farPlane.w = clip.m33 - clip.m32
        normalize(farPlane)

        // Calculate near plane
        def nearPlane = new Vector4f()
        nearPlane.x = clip.m03 + clip.m02
        nearPlane.y = clip.m13 + clip.m12
        nearPlane.z = clip.m23 + clip.m22
        nearPlane.w = clip.m33 + clip.m32
        normalize(nearPlane)

        return [rightPlane, leftPlane, botPlane, topPlane, farPlane, nearPlane]
    }
}
