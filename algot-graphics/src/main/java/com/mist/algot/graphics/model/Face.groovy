package com.mist.algot.graphics.model

class Face {

    private final TexturedVertex first, second, third

    public Face(TexturedVertex first, TexturedVertex second, TexturedVertex third) {
        this.first = first
        this.second = second
        this.third = third
    }

    TexturedVertex getFirst() {
        return first
    }

    TexturedVertex getSecond() {
        return second
    }

    TexturedVertex getThird() {
        return third
    }
}
