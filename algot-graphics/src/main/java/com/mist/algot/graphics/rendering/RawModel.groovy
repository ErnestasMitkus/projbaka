package com.mist.algot.graphics.rendering

class RawModel {

    private int vaoId
    private int vertexCount

    RawModel(int vaoId, int vertexCount) {
        this.vaoId = vaoId
        this.vertexCount = vertexCount
    }

    int getVaoId() {
        return vaoId
    }

    int getVertexCount() {
        return vertexCount
    }
}
