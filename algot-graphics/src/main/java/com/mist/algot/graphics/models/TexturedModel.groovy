package com.mist.algot.graphics.models

import com.mist.algot.graphics.textures.ModelTexture

class TexturedModel {

    private RawModel rawModel
    private ModelTexture texture

    TexturedModel(RawModel rawModel, ModelTexture texture) {
        this.rawModel = rawModel
        this.texture = texture
    }

    RawModel getRawModel() {
        return rawModel
    }

    ModelTexture getTexture() {
        return texture
    }
}
