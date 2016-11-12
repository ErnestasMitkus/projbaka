package com.mist.algot.temp

import com.mist.algot.graphics.model.Vertex
import org.lwjgl.util.vector.Vector2f

class LineObj implements RenderableObj {
    @Override
    List<Vertex> getVertices() {
        return [new Vertex(0, 0, 0), new Vertex(0, 0.25f, 0), new Vertex(0.25f, 0.25f, 0)]
    }

    @Override
    List<Indice> getIndices() {
        return [new Indice(0, 1, 2)]
    }

    @Override
    List<Vector2f> getTextureCoords() {
        return [new Vector2f(), new Vector2f(), new Vector2f()]
    }

    @Override
    int getTextureId() {
        return 0
    }
}
