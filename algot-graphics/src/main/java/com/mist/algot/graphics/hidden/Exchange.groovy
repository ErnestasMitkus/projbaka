package com.mist.algot.graphics.hidden

import com.mist.algot.graphics.entities.Entity
import com.mist.algot.graphics.models.TexturedModel

class Exchange {

    private final List<Entity> entities
    private final Map<String, Object> properties

    private Exchange(List<Entity> entities) {
        this.entities = entities
        this.properties = [:]
    }

    static Exchange fromEntitiesMap(Map<TexturedModel, List<Entity>> entitiesMap) {
        List<Entity> entities = []
        entitiesMap.keySet().each {
            entities.addAll entitiesMap[it]
        }
        fromEntities(entities)
    }

    static Exchange fromEntities(List<Entity> entities) {
        new Exchange(entities)
    }

    List<Entity> getEntities() {
        entities
    }

    Map<String, Object> getProperties() {
        properties
    }

    boolean has(String name) {
        properties[name]
    }

    Object getProperty(String name) {
        properties[name]
    }

    public <T> T getProperty(String name, Class<T> clazz) {
        clazz.cast properties[name]
    }

    void setProperty(String name, value) {
        properties.put(name, value)
    }

    Exchange withProperty(String name, value) {
        setProperty(name, value)
        this
    }
}
