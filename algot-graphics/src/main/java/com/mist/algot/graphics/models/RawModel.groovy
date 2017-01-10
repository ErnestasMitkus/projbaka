package com.mist.algot.graphics.models

import com.mist.algot.graphics.model.Indice
import com.mist.algot.graphics.model.Mesh

class RawModel {

    private final int vaoId
    private final int indicesVbo
    private final Mesh mesh
    private List<Indice> calculatedIndices

    RawModel(int vaoId, int indicesVbo, Mesh mesh) {
        this.vaoId = vaoId
        this.indicesVbo = indicesVbo
        this.mesh = mesh
    }

    int getVaoId() {
        return vaoId
    }

    int getIndicesVbo() {
        indicesVbo
    }

    Mesh getMesh() {
        return mesh
    }

    List<Indice> getCalculatedIndices() {
        return calculatedIndices
    }

    void setCalculatedIndices(List<Indice> calculatedIndices) {
        this.calculatedIndices = calculatedIndices
    }
}
