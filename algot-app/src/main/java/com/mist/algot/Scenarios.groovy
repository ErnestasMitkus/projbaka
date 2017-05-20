package com.mist.algot

import com.mist.algot.graphics.entities.Entity
import com.mist.algot.graphics.models.RawModel
import com.mist.algot.graphics.models.TexturedModel
import com.mist.algot.graphics.rendering.Loader
import com.mist.algot.graphics.textures.ModelTexture
import com.mist.algot.graphics.utils.OBJLoader
import org.lwjgl.util.vector.Vector3f

class Scenarios {

    private static RawModel DRAGON_RAWMODEL
    private static ModelTexture DRAGON_TEXTURE
    private static TexturedModel DRAGON_MODEL

    private static RawModel TREE_RAWMODEL
    private static ModelTexture TREE_TEXTURE
    private static TexturedModel TREE_MODEL

    private static RawModel STALL_RAWMODEL
    private static ModelTexture STALL_TEXTURE
    private static TexturedModel STALL_MODEL

    private static RawModel CUBE_RAWMODEL
    private static ModelTexture CUBE_TEXTURE
    private static TexturedModel CUBE_MODEL

    static void loadModels(Loader loader) {
        DRAGON_RAWMODEL = OBJLoader.loadObjModel("/objects/dragon.obj", loader)
        DRAGON_TEXTURE = new ModelTexture(loader.loadTexture("/textures/lamp.png"))
        DRAGON_TEXTURE.shineDamper = 10
        DRAGON_TEXTURE.reflectivity = 1
        DRAGON_MODEL = new TexturedModel(DRAGON_RAWMODEL, DRAGON_TEXTURE)

        TREE_RAWMODEL = OBJLoader.loadObjModel("/objects/lowPolyTree.obj", loader)
        TREE_TEXTURE = new ModelTexture(loader.loadTexture("/textures/lowPolyTree.png"))
        TREE_TEXTURE.shineDamper = 10
        TREE_TEXTURE.reflectivity = 1
        TREE_MODEL = new TexturedModel(TREE_RAWMODEL, TREE_TEXTURE)

        STALL_RAWMODEL = OBJLoader.loadObjModel("/objects/stall.obj", loader)
        STALL_TEXTURE = new ModelTexture(loader.loadTexture("/textures/stallTexture.png"))
        STALL_TEXTURE.shineDamper = 10
        STALL_TEXTURE.reflectivity = 1
        STALL_MODEL = new TexturedModel(STALL_RAWMODEL, STALL_TEXTURE)

        CUBE_RAWMODEL = OBJLoader.loadObjModel("/objects/simpleCube.obj", loader)
        CUBE_TEXTURE = new ModelTexture(loader.loadTexture("/textures/mud.png"))
        CUBE_TEXTURE.shineDamper = 10
        CUBE_TEXTURE.reflectivity = 1
        CUBE_MODEL = new TexturedModel(CUBE_RAWMODEL, CUBE_TEXTURE)
    }

    static List<Entity> cube() {
        [
            new Vector3f(0, -1, -7),
        ].collect {
            new Entity(CUBE_MODEL, it)
        }
    }

    static List<Entity> dragons() {
        [
            new Vector3f(0, -5, -25)
        ].collect {
            new Entity(DRAGON_MODEL, it)
        }
    }

    static List<Entity> trees() {
        float dist = 100
        float dist1 = 150
        float dist2 = 200
        float dist3 = 200
        float dist4 = 50
        float toRad = Math.PI / 180f
        def aroundTrees = (0..29).collect {
            def radians = (it * (360f / 30f)) * toRad
            def radians1 = (it * (360f / 30f) + 6f) * toRad
            def radians2 = (it * (360f / 30f) + 3f) * toRad
            def radians3 = (it * (360f / 30f) - 3f) * toRad
            def radians4 = (it * (360f / 30f)) * toRad

            [new Vector3f(dist * Math.sin(radians) as float, 0, dist * Math.cos(radians) as float),
             new Vector3f(dist1 * Math.sin(radians1) as float, -6, dist1 * Math.cos(radians1) as float),
             new Vector3f(dist2 * Math.sin(radians2) as float, 40, dist2 * Math.cos(radians2) as float),
             new Vector3f(dist3 * Math.sin(radians3) as float, 40, dist3 * Math.cos(radians3) as float)
            ] + (it % 2 == 0 ? [new Vector3f(dist4 * Math.sin(radians4) as float, -10, dist4 * Math.cos(radians4) as float)] : [])
        }.flatten() as List<Vector3f>

        aroundTrees.collect {
            new Entity(TREE_MODEL, it)
        }
    }

    static List<Entity> scenarioFromName(String name) {
        Map<String, Closure<List<Entity>>> scenarios = [
            cube: Scenarios.&cube,
            dragons: Scenarios.&dragons,
            trees: Scenarios.&trees,
        ]
        scenarios[name].call()
    }
}
