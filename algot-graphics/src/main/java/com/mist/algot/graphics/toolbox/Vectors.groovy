package com.mist.algot.graphics.toolbox

import org.lwjgl.util.vector.Vector3f

class Vectors {

    static Vector3f plus(Vector3f a, Vector3f b) {
        new Vector3f(a.x + b.x as float, a.y + b.y as float, a.z + b.z as float)
    }

    static Vector3f minus(Vector3f a, Vector3f b) {
        new Vector3f(a.x - b.x as float, a.y - b.y as float, a.z - b.z as float)
    }

    static Vector3f multiply(Vector3f a, float b) {
        new Vector3f(a).scale(b) as Vector3f
    }

    static Vector3f multiply(Vector3f a, double b) {
        multiply(a, b as float)
    }

    static Vector3f multiply(Vector3f a, Vector3f b) {
        return new Vector3f(a.x * b.x as float, a.y * b.y as float, a.z * b.z as float)
    }

    static float[] toArray(List<Vector3f> vectors) {
        float[] result = new float[vectors.size() * 3]
        int index = 0
        vectors.each {
            result[index++] = it.x
            result[index++] = it.y
            result[index++] = it.z
        }
        result
    }
}
