package com.mist.algot.graphics.toolbox

import com.mist.algot.graphics.entities.FrustumPlane
import org.lwjgl.util.vector.Vector3f
import org.newdawn.slick.Color

class FrustumHelpers {

    private static final float ALPHA_FACTOR = 0.7;
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
//        def near = frustumPlanes[5]
//        def far = frustumPlanes[4]

        def result = []

        frustumPlanes.each {
            it.points().each { result << it }
        }

        result
//        [far.a, far.d, far.b, far.c, near.d, near.a, near.c, near.b]
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
