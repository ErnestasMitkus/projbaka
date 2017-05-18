package com.mist.algot.graphics.toolbox

import com.mist.algot.graphics.entities.FrustumPlane
import org.lwjgl.util.vector.Vector3f
import org.lwjgl.util.vector.Vector4f
import org.newdawn.slick.Color

class FrustumHelpers {

    private static final float ALPHA_FACTOR = 0.7
    private static final Color COLOR_FAR = trans(new Color(200, 0, 200), ALPHA_FACTOR)
    private static final Color COLOR_NEAR = trans(Color.cyan, ALPHA_FACTOR)
    private static final Color COLOR_RIGHT = trans(Color.yellow, ALPHA_FACTOR)
    private static final Color COLOR_LEFT = trans(Color.green, ALPHA_FACTOR)
    private static final Color COLOR_TOP = trans(Color.blue, ALPHA_FACTOR)
    private static final Color COLOR_BOT = trans(Color.orange, ALPHA_FACTOR)

    @SuppressWarnings("GroovyAssignabilityCheck")
    static List<FrustumPlane> transformToPlanes(List<Vector3f> points) {
        assert points.size() == 8
        def (ftl, ftr, fbl, fbr, ntl, ntr, nbl, nbr) = points


        def far = new FrustumPlane(ftl, fbl, fbr, ftr, COLOR_FAR)
        def near = new FrustumPlane(ntr, nbr, nbl, ntl, COLOR_NEAR)
        def right = new FrustumPlane(ftr, fbr, nbr, ntr, COLOR_RIGHT)
        def left = new FrustumPlane(ntl, nbl, fbl, ftl, COLOR_LEFT)
        def top = new FrustumPlane(ntl, ftl, ftr, ntr, COLOR_TOP)
        def bottom = new FrustumPlane(fbl, nbl, nbr, fbr, COLOR_BOT)

         // right left bottom top far near
        [right, left, bottom, top, far, near]
    }

    static List<Vector3f> extractPoints(List<FrustumPlane> frustumPlanes) {
        frustumPlanes*.points.flatten() as List<Vector3f>
    }

    @SuppressWarnings("GroovyAssignabilityCheck")
    static List<Vector4f> extractPlanes(List<FrustumPlane> frustumPlanes) {
        use(Vectors) {
            frustumPlanes.collect { plane ->
                def vec1 = plane.b - plane.a
                def vec2 = plane.c - plane.a

                def crossProduct = Vector3f.cross(vec1, vec2, null)
                def vec = new Vector4f(
                    crossProduct.x,
                    crossProduct.y,
                    crossProduct.z,
                    - (crossProduct.x * plane.a.x + crossProduct.y * plane.a.y + crossProduct.z * plane.a.z) as float
                )
                vec.normalise(null)
            }
        }
    }

    static List<Integer> extractIndices(List<FrustumPlane> frustumPlanes) {
        def result = []
        def points = extractPoints(frustumPlanes)

        frustumPlanes.eachWithIndex { it, index ->
            int startIndex = index * 4
            def planePoints = points[startIndex..startIndex + 3]
            it.plane1.each {
                result << planePoints.indexOf(it) + startIndex
            }
            it.plane2.each {
                result << planePoints.indexOf(it) + startIndex
            }
        }
        result
    }

    private static Color trans(Color color, float alpha) {
        color.a = alpha
        color
    }
}
