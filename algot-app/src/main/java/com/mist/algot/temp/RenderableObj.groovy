package com.mist.algot.temp

import com.mist.algot.graphics.model.Vertex
import org.lwjgl.util.vector.Vector2f

interface RenderableObj {

    List<Vertex> getVertices()
    List<Indice> getIndices()
    List<Vector2f> getTextureCoords()

    int getTextureId()

}
