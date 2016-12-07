package com.mist.algot.graphics.utils

import com.google.common.base.Function
import com.google.common.collect.Iterables
import com.google.common.collect.Lists
import com.mist.algot.graphics.model.Face
import com.mist.algot.graphics.model.Indice
import com.mist.algot.graphics.model.Mesh
import com.mist.algot.graphics.model.Normal
import com.mist.algot.graphics.model.Vertex
import org.lwjgl.util.vector.Vector2f
import org.lwjgl.util.vector.Vector3f

class ModelsHelpers {

    static List<Vector3f> transformToVerticesPositionList(float[] vertices) {
        assert vertices.length % 3 == 0 : "Vertices must be a divisor of 3"
        List<Vector3f> list = []
        for (int i = 0; i < vertices.length; i += 3) {
            list.add(new Vector3f(vertices[i], vertices[i+1], vertices[i+2]))
        }
        list
    }

    static List<Vector2f> transformToTextureCoordsList(float[] texCoords) {
        assert texCoords.length % 2 == 0 : "Texture coords must be a divisor of 2"
        List<Vector2f> list = []
        for (int i = 0; i < texCoords.length; i += 2) {
            list.add(new Vector2f(texCoords[i], texCoords[i+1]))
        }
        list
    }

    static List<Normal> transformToNormalsList(float[] normals) {
        assert normals.length % 3 == 0 : "Normals must be a divisor of 3"
        List<Normal> list = []
        for (int i = 0; i < normals.length; i += 3) {
            list.add(new Normal(normals[i], normals[i + 1], normals[i + 2]))
        }
        list
    }

    static List<Indice> transformToIndicesList(int[] indices) {
        assert indices.length % 3 == 0 : "Indices must be a divisor of 3"
        List<Indice> list = []
        for (int i = 0; i < indices.length; i += 3) {
            list.add(new Indice(indices[i], indices[i+1], indices[i+2]))
        }
        list
    }

    static int[] transformToPrimitiveIndices(List<Indice> indices) {
        int[] list = new int[indices.size() * 3]
        indices.eachWithIndex { Indice indice, int i ->
            list[i * 3] = indice.x
            list[i * 3 + 1] = indice.y
            list[i * 3 + 2] = indice.z
        }
        list
    }

    static List<Vertex> transformToVerticesList(List<Vector3f> positions, List<Vector2f> textureCoords, List<Normal> normals) {
        assert positions.size() == normals.size() : "There should be the same amount of normals as there are vertices. ${positions.size()} != ${normals.size()}"
        assert positions.size() == textureCoords.size() : "There should be the same amount of texture coordinates as there are vertices. ${positions.size()} != ${textureCoords.size()}"
        List<Vertex> list = []
        positions.eachWithIndex { Vector3f position, int i ->
            list.add(Vertex.texturedVertex(position, textureCoords[i], normals[i]))
        }
        list
    }

    static List<Vertex> transformToVerticesList(List<Vector3f> positions, List<Normal> normals) {
        assert positions.size() == normals.size() : "There should be the same amount of normals as there are vertices. ${positions.size()} != ${normals.size()}"
        List<Vertex> list = []
        positions.eachWithIndex { Vector3f position, int i ->
            list.add(Vertex.vertex(position, normals[i]))
        }
        list
    }

    static List<Face> calculateFaces(List<Vertex> vertices, List<Indice> indices) {
        List<Face> faces = Lists.newArrayList()
        indices.each {
            faces.add(new Face(vertices[it.x], vertices[it.y], vertices[it.z], it))
        }
        faces
    }

    static List<Indice> extractIndices(List<Face> faces) {
        Lists.newArrayList(Iterables.transform(faces, { Face face ->
            face.indice
        } as Function<Face, Indice>))
    }

    static Mesh createMesh(float[] positions, float[] textureCoords, float[] normals, int[] indices) {
        List<Vertex> vertices = transformToVerticesList(
                transformToVerticesPositionList(positions),
                transformToTextureCoordsList(textureCoords),
                transformToNormalsList(normals))
        List<Indice> indicesList = transformToIndicesList(indices)
        new Mesh(vertices, indicesList)
    }
}
