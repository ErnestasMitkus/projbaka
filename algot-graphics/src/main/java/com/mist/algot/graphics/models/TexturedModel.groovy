package com.mist.algot.graphics.models

import com.mist.algot.graphics.textures.ModelTexture

class TexturedModel {

    private RawModel rawModel
    private ModelTexture texture
    private BoundingSphere boundingSphere

    TexturedModel(RawModel rawModel, ModelTexture texture, BoundingSphere boundingSphere = new BoundingSphere(-1f)) {
        this.rawModel = rawModel
        this.texture = texture
        this.boundingSphere = boundingSphere
    }

    RawModel getRawModel() {
        return rawModel
    }

    ModelTexture getTexture() {
        return texture
    }

    BoundingSphere getBoundingSphere() {
        boundingSphere
    }
}
