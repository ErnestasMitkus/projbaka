package com.mist.algot.graphics.toolbox

import com.mist.algot.graphics.entities.FrustumPlane
import org.lwjgl.util.vector.Vector3f
import org.lwjgl.util.vector.Vector4f
import spock.lang.Specification

class FrustumHelpersTest extends Specification {

    def "should correctly calculate plane"() {
        when:
        def plane = new FrustumPlane(new Vector3f(1, 0, 2), new Vector3f(-1, 1, 2), new Vector3f(5, 0, 3), null)
        def planeEquation = FrustumHelpers.extractPlanes([plane])

        then:
        equals(planeEquation[0], new Vector4f(1, 2, -4, 7).normalise(null))
    }

    static boolean equals(Vector4f vec1, Vector4f vec2) {
        vec1.x == vec2.x && vec1.y == vec2.y && vec1.z == vec2.z && vec1.w == vec2.w
    }
}
