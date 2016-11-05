package com.mist.algot.graphics.shaders

import com.mist.algot.graphics.rendering.Loader

class StaticShader extends ShaderProgram {

    private static final String VERTEX_FILE = "/shaders/main.vert"
    private static final String FRAGMENT_FILE = "/shaders/main.frag"

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE)
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(Loader.VERTICES_ATTRIB_INDEX, "position")
        bindAttribute(Loader.TEXTURE_COORDINATES_ATTRIB_INDEX, "textureCoords")
    }
}
