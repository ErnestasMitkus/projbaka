package com.mist.algot.temp

import com.mist.algot.graphics.model.TexturedVertex
import com.mist.algot.graphics.model.VTriangle
import com.mist.algot.graphics.model.Vertex
import org.lwjgl.util.vector.Vector2f

class ObjHelpers {

    static List<Vertex> transformToList(float[] vertices) {
        assert vertices.length % 3 == 0 : "Vertices must be a divisor of 3"

        List<Vertex> verts = []
        for (int i = 0; i < vertices.length; i += 3) {
            verts.add(new Vertex(vertices[i], vertices[i+1], vertices[i+2]))
        }
        return verts
    }

    static List<TexturedVertex> enrichVertices(List<Vertex> vertices, List<Vector2f> texCoords) {
        assert vertices.size() == texCoords.size() : "There should be the same amount of texture coordinates as there is vertices. ${vertices.size()} != ${texCoords.size()}"
        List<TexturedVertex> list = []
        for (int i = 0; i < vertices.size(); i++) {
            list.add(new TexturedVertex(vertices[i].x, vertices[i].y, vertices[i].z, texCoords[i].x, texCoords[i].y))
        }
        return list
    }

    static List<Indice> transformToList(int[] indices) {
        assert indices.length % 3 == 0 : "Indices must be a divisor of 3"

        List<Indice> inds = []
        for (int i = 0; i < indices.length; i += 3) {
            inds.add(new Indice(indices[i], indices[i+1], indices[i+2]))
        }
        return inds
    }

    static List<Vector2f> transformTextureCoordsToList(float[] texCoords) {
        assert texCoords.length % 2 == 0 : "Texture coords must be a divisor of 3"
        List<Vector2f> textureCoords = []
        for (int i = 0; i < texCoords.length; i += 2) {
            textureCoords.add(new Vector2f(texCoords[i], texCoords[i+1]))
        }
        return textureCoords
    }

    static List<VTriangle> calculateTriangles(float[] vertices, float[] texCoords, int[] indices) {
        calculateTriangles(
                enrichVertices(transformToList(vertices), transformTextureCoordsToList(texCoords)),
                transformToList(indices))
    }

    static List<VTriangle> calculateTriangles(List<TexturedVertex> vertices, List<Indice> indices) {
        List<VTriangle> triangles = []
        for (Indice ind : indices) {
            triangles.add(new VTriangle(vertices.get(ind.x), vertices.get(ind.y), vertices.get(ind.z)))
        }
        return triangles
    }

}
