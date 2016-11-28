package com.mist.algot.graphics.models

class RawModel {

    private int vaoId
    private int indicesVbo
    private int[] indices
    private int counter = 1
    private int z = 1
    private int fake = 0

    RawModel(int vaoId, int indicesVbo, int[] indices) {
        this.vaoId = vaoId
        this.indicesVbo = indicesVbo
        this.indices = indices
    }

    int getVaoId() {
        return vaoId
    }

    int getIndicesVbo() {
        indicesVbo
    }

    int[] getIndices() {
        if (counter >= 10) {
            z = -1
        }
        if (counter <= 1) {
            z = 1
        }
        fake++
        if (fake % 30 == 0) {
            counter += z
        }
        int len = indices.length / counter
        int[] ind = new int[len]
        for (int i = 0; i < len; i++) {
            ind[i] = indices[i]
        }

        return ind
    }
}
