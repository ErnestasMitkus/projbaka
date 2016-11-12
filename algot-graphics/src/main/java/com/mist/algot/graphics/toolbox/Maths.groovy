package com.mist.algot.graphics.toolbox

import org.lwjgl.util.vector.Matrix3f
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

    static Vector4f multiply(Matrix4f mat, Vector4f vec) {
        Vector4f result = new Vector4f()
        result.with {
            x = mat.m00*vec.x + mat.m10*vec.y + mat.m20*vec.z + mat.m30*vec.w
            y = mat.m01*vec.x + mat.m11*vec.y + mat.m21*vec.z + mat.m31*vec.w
            z = mat.m02*vec.x + mat.m12*vec.y + mat.m22*vec.z + mat.m32*vec.w
            w = mat.m03*vec.x + mat.m13*vec.y + mat.m23*vec.z + mat.m33*vec.w
        }
        return result
    }

    static Vector3f multiply(Matrix3f mat, Vector3f vec) {
        Vector3f result = new Vector3f()
        result.with {
            x = mat.m00*vec.x + mat.m10*vec.y + mat.m20*vec.z
            y = mat.m01*vec.x + mat.m11*vec.y + mat.m21*vec.z
            z = mat.m02*vec.x + mat.m12*vec.y + mat.m22*vec.z
        }
        return result
    }

    static Vector3f normalize(Vector4f vector) {
        float w = vector.w
        new Vector3f((float)(vector.x / w), (float)(vector.y / w), (float)(vector.z / w))
    }

    static Matrix4f lookAt(Vector3f eye, Vector3f center, Vector3f up) {
        Vector3f f = Vector3f.sub(center, eye, null).normalise(null)
        Vector3f u = up.normalise(null)
        Vector3f s = Vector3f.cross(f, u, null).normalise(null)
        u = Vector3f.cross(s, f, null)

        Matrix4f result = new Matrix4f();
        result.m00 = s.x  //result.set(0, 0, s.x);
        result.m10 = s.y  // result.set(1, 0, s.y);
        result.m20 = s.z  // result.set(2, 0, s.z);
        result.m01 = u.x  // result.set(0, 1, u.x);
        result.m11 = u.y  // result.set(1, 1, u.y);
        result.m21 = u.z  // result.set(2, 1, u.z);
        result.m02 = -f.x // result.set(0, 2, -f.x);
        result.m12 = -f.y // result.set(1, 2, -f.y);
        result.m22 = -f.z // result.set(2, 2, -f.z);

        Matrix4f.translate(new Vector3f(-eye.x, -eye.y, -eye.z), result, null)
    }

    static Vector3f mul(Vector3f vector, float amount) {
        new Vector3f((float)(vector.x * amount), (float)(vector.y * amount), (float)(vector.z * amount))
    }

    static Vector3f add(Vector3f vector, float amount) {
        new Vector3f((float)(vector.x + amount), (float)(vector.y + amount), (float)(vector.z + amount))
    }

    static Vector3f add(Vector3f vector, Vector3f amounts) {
        new Vector3f((float)(vector.x + amounts.x), (float)(vector.y + amounts.y), (float)(vector.z + amounts.z))
    }

    static Matrix3f reduce(Matrix4f matrix) {
        Matrix3f result = new Matrix3f()
        result.with {
            m00 = matrix.m00
            m01 = matrix.m01
            m02 = matrix.m02

            m10 = matrix.m10
            m11 = matrix.m11
            m12 = matrix.m12

            m20 = matrix.m20
            m21 = matrix.m21
            m22 = matrix.m22
        }
        return result
    }

    static Vector3f reduce(Vector4f vector) {
        new Vector3f(vector.x, vector.y, vector.z)
    }

}
