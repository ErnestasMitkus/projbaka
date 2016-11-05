package com.mist.algot.graphics.shaders

class StaticShader extends ShaderProgram {

    private static final String VERTEX_FILE = "/shaders/main.vert"
    private static final String FRAGMENT_FILE = "/shaders/main.frag"

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE)
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(0, "position")
    }
}
