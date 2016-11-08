package com.mist.algot.temp

import com.mist.algot.graphics.model.Vertex
import com.mist.algot.graphics.rendering.FlatRenderer

class CubeObj {

    static float[] vertices = [
        -0.5f,0.5f,-0.5f,
        -0.5f,-0.5f,-0.5f,
        0.5f,-0.5f,-0.5f,
        0.5f,0.5f,-0.5f,

        -0.5f,0.5f,0.5f,
        -0.5f,-0.5f,0.5f,
        0.5f,-0.5f,0.5f,
        0.5f,0.5f,0.5f,

        0.5f,0.5f,-0.5f,
        0.5f,-0.5f,-0.5f,
        0.5f,-0.5f,0.5f,
        0.5f,0.5f,0.5f,

        -0.5f,0.5f,-0.5f,
        -0.5f,-0.5f,-0.5f,
        -0.5f,-0.5f,0.5f,
        -0.5f,0.5f,0.5f,

        -0.5f,0.5f,0.5f,
        -0.5f,0.5f,-0.5f,
        0.5f,0.5f,-0.5f,
        0.5f,0.5f,0.5f,

        -0.5f,-0.5f,0.5f,
        -0.5f,-0.5f,-0.5f,
        0.5f,-0.5f,-0.5f,
        0.5f,-0.5f,0.5f];

    static final float[] textureCoords = [
        0,0,
        0,1,
        1,1,
        1,0,
        0,0,
        0,1,
        1,1,
        1,0,
        0,0,
        0,1,
        1,1,
        1,0,
        0,0,
        0,1,
        1,1,
        1,0,
        0,0,
        0,1,
        1,1,
        1,0,
        0,0,
        0,1,
        1,1,
        1,0];

    static final int[] indices = [
        0,1,3,
        3,1,2,
        4,5,7,
        7,5,6,
        8,9,11,
        11,9,10,
        12,13,15,
        15,13,14,
        16,17,19,
        19,17,18,
        20,21,23,
        23,21,22];

    public void render(FlatRenderer renderer) {
        for (int i = 0; i < indices.length; i+= 3) {
            int vert1Loc = indices[i]
            int vert2Loc = indices[i+1]
            int vert3Loc = indices[i+2]
            Vertex v1 = new Vertex(vertices[vert1Loc], vertices[vert1Loc+1], vertices[vert1Loc+2])
            Vertex v2 = new Vertex(vertices[vert2Loc], vertices[vert2Loc+1], vertices[vert2Loc+2])
            Vertex v3 = new Vertex(vertices[vert3Loc], vertices[vert3Loc+1], vertices[vert3Loc+2])

            renderer.render([v1, v2, v3])
        }
    }
}
