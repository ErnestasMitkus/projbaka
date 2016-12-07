package com.mist.algot.graphics.model

import com.google.common.collect.ImmutableList
import com.mist.algot.graphics.utils.ModelsHelpers

class Mesh {
    private ImmutableList<Vertex> vertices
    private ImmutableList<Indice> indices
    private ImmutableList<Face> faces

    Mesh(List<Vertex> vertices, List<Indice> indices) {
        this.vertices = ImmutableList.copyOf(vertices)
        this.indices = ImmutableList.copyOf(indices)
        this.faces = ImmutableList.copyOf(ModelsHelpers.calculateFaces(vertices, indices))
    }

    ImmutableList<Vertex> getVertices() {
        vertices
    }

    ImmutableList<Indice> getIndices() {
        indices
    }

    ImmutableList<Face> getFaces() {
        faces
    }
}
