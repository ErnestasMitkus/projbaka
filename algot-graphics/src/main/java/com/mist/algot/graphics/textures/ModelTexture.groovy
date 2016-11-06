package com.mist.algot.graphics.textures

class ModelTexture {

    private int textureId

    private float shineDamper = 1
    private float reflectivity

    ModelTexture(int textureId) {
        this.textureId = textureId
    }

    int getId() {
        return textureId
    }

    float getShineDamper() {
        return shineDamper
    }

    void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper
    }

    float getReflectivity() {
        return reflectivity
    }

    void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity
    }
}
