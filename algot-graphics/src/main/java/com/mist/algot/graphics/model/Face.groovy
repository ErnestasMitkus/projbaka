package com.mist.algot.graphics.model

class Face {
    private final Vertex first, second, third
    private final Indice indice

    Face(Vertex first, Vertex second, Vertex third, Indice indice) {
        this.first = first
        this.second = second
        this.third = third
        this.indice = indice
    }

    Vertex getFirst() {
        return first
    }

    Vertex getSecond() {
        return second
    }

    Vertex getThird() {
        return third
    }

    Indice getIndice() {
        return indice
    }
}
