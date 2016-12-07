package com.mist.algot.graphics.model

import org.lwjgl.util.vector.Vector2f
import org.lwjgl.util.vector.Vector3f

class Vertex {
    private final Vector3f position
    private final Vector2f textureCoords
    private final Normal normal

    private Vertex(Vector3f position, Vector2f textureCoords, Normal normal) {
        this.position = position
        this.textureCoords = textureCoords
        this.normal = normal
    }

    private Vertex(Vector3f position, Normal normal) {
        this.position = position
        this.textureCoords = null
        this.normal = normal
    }

    static Vertex texturedVertex(Vector3f position, Vector2f textureCoords, Normal normal) {
        new Vertex(position, textureCoords, normal)
    }

    static Vertex vertex(Vector3f position, Normal normal) {
        new Vertex(position, normal)
    }

    Vector3f getPosition() {
        return position
    }

    Vector2f getTextureCoords() {
        return textureCoords
    }

    Normal getNormal() {
        return normal
    }

    boolean isTextured() {
        textureCoords != null
    }
}
