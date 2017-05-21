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

    static List<Vector3f> calculateFrustumPlanesPoints(Camera camera) {
        Vector3f direction = camera.viewDirection
        Vector3f position = camera.position
        Vector3f up = camera.up
        Vector3f right = camera.right

        float nearDist = Renderer.NEAR_PLANE
        float farDist = Renderer.FAR_PLANE

        float Hnear = 4f
        float Wnear = 7f
        float Hfar = 394f
        float Wfar = 700f

        // overrides
        nearDist = 20f
        farDist = 200f
        Hnear = 10f
        Wnear = 10f
        Hfar = 120f
        Wfar = 120f

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

    static boolean sphereInsideFrustum(Vector3f position, float sphereRadius, List<Vector4f> frustumPlanes) {
        for (def plane: frustumPlanes) {
            float distance = distanceFromPlane(plane, position)
            if (distance < -sphereRadius) {
                return false
            }
        }
        return true
    }

    private static float distanceFromPlane(Vector4f plane, Vector3f point) {
        // dot product + plane distance from origin
        (plane.x * point.x + plane.y * point.y + plane.z * point.z + plane.w) as float
    }
}
