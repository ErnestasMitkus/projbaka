package com.mist.algot.temp

import com.mist.algot.graphics.model.Indice
import com.mist.algot.graphics.model.Vertex
import com.mist.algot.graphics.utils.FileUtils
import org.lwjgl.util.vector.Vector2f
import org.newdawn.slick.opengl.Texture
import org.newdawn.slick.opengl.TextureLoader

class TestObj {

    static float[] STATIC_vertices = [
        -0.5f, -0.5f, 0,
        -0.5f, 0.5f, 0,
        0.5f, 0.5f, 0,
        0.5f, -0.5f, 0];

    static final int[] STATIC_indices = [
        1, 0, 2,
        2, 0, 3
    ];

    static final float[] STATIC_textureCoords = [
        0, 1,
        0, 0,
        1, 0,
        1, 1
    ]

    private final List<Vertex> vertices
    private final List<Indice> indices
    private final List<Vector2f> textureCoords

    private final int textureId

    public TestObj(String fileName) {
        vertices = ObjHelpers.transformToList(STATIC_vertices)
        indices = ObjHelpers.transformToList(STATIC_indices)
        textureCoords = ObjHelpers.transformTextureCoordsToList(STATIC_textureCoords)

        Texture texture = TextureLoader.getTexture("PNG", FileUtils.loadFile(fileName))
        textureId = texture.textureID
    }
}
