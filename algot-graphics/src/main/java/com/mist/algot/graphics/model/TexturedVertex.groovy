package com.mist.algot.graphics.model

class TexturedVertex extends Vertex {

    float texCoordX, texCoordY

    TexturedVertex(float x, float y, float z, float texCoordX, float texCoordY) {
        super(x, y, z)
        this.texCoordX = texCoordX
        this.texCoordY = texCoordY
    }

}
