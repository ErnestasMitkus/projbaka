package com.mist.algot.graphics.toolbox

import com.mist.algot.graphics.entities.FrustumPlane
import org.lwjgl.util.vector.Vector3f
import org.lwjgl.util.vector.Vector4f
import spock.lang.Specification

class FrustumHelpersTest extends Specification {

    @SuppressWarnings("GroovyAssignabilityCheck")
    def "foo"() {
        when:
        def plane = new FrustumPlane(new Vector3f(1, 0, 2), new Vector3f(-1, 1, 2), new Vector3f(5, 0, 3), null)
        def (vec1, vec2, crossProduct, vec) = [null, null, null, null]
        use(Vectors) {
            vec1 = plane.b - plane.a
            vec2 = plane.c - plane.a

            crossProduct = Vector3f.cross(vec1, vec2, null)
            vec = new Vector4f(
                    crossProduct.x,
                    crossProduct.y,
                    crossProduct.z,
                    - (crossProduct.x * plane.a.x + crossProduct.y * plane.a.y + crossProduct.z * plane.a.z) as float
            )
            return vec
        }

        then:
        equals(vec1, new Vector3f(-2, 1, 0))
        equals(vec2, new Vector3f(4, 0, 1))
        equals(crossProduct, new Vector3f(1, 2, -4))
        equals(vec, new Vector4f(1, 2, -4, 7))
    }

    static boolean equals(Vector3f vec1, Vector3f vec2) {
        vec1.x == vec2.x && vec1.y == vec2.y && vec1.z == vec2.z
    }
    static boolean equals(Vector4f vec1, Vector4f vec2) {
        vec1.x == vec2.x && vec1.y == vec2.y && vec1.z == vec2.z && vec1.w == vec2.w
    }
}
