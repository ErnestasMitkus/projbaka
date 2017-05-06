package com.mist.algot.graphics.toolbox

import com.mist.algot.graphics.entities.Camera
import com.mist.algot.graphics.rendering.Renderer
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

    static Vector4f multiply(Matrix4f matrix, Vector4f vector) {
        new Vector4f(
            matrix.m00 * vector.x + matrix.m01 * vector.y + matrix.m02 * vector.z + matrix.m03 * vector.y as float,
            matrix.m10 * vector.x + matrix.m11 * vector.y + matrix.m12 * vector.z + matrix.m13 * vector.y as float,
            matrix.m20 * vector.x + matrix.m21 * vector.y + matrix.m22 * vector.z + matrix.m23 * vector.y as float,
            matrix.m30 * vector.x + matrix.m31 * vector.y + matrix.m32 * vector.z + matrix.m33 * vector.y as float
        )
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

    static List<Vector3f> calculateFrustumPlanesPoints(Camera camera) {
        Vector3f direction = camera.viewDirection
        Vector3f position = camera.position
        Vector3f up = camera.up
        Vector3f right = camera.right

        float nearDist = 15 //Renderer.NEAR_PLANE
        float farDist = 200f //Renderer.FAR_PLANE

        float Hnear = 10f
        float Wnear = 10f
        float Hfar = 120f
        float Wfar = 120f

        use(Vectors) {
            Vector3f farCenter = position + direction * farDist
            Vector3f nearCenter = position + direction * nearDist
            Vector3f ftl, ftr, fbl, fbr, ntl, ntr, nbl, nbr  // far top left, top right, bot left, bot right, near ...

            ftl = farCenter + (up * (Hfar / 2)) - (right * (Wfar / 2))
            ftr = farCenter + (up * (Hfar / 2)) + (right * (Wfar / 2))
            fbl = farCenter - (up * (Hfar / 2)) - (right * (Wfar / 2))
            fbr = farCenter - (up * (Hfar / 2)) + (right * (Wfar / 2))

            ntl = nearCenter + (up * (Hnear / 2)) - (right * (Wnear / 2))
            ntr = nearCenter + (up * (Hnear / 2)) + (right * (Wnear / 2))
            nbl = nearCenter - (up * (Hnear / 2)) - (right * (Wnear / 2))
            nbr = nearCenter - (up * (Hnear / 2)) + (right * (Wnear / 2))

            return [ftl, ftr, fbl, fbr, ntl, ntr, nbl, nbr]
        }
    }
}
