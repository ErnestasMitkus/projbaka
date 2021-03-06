package com.mist.algot

import com.mist.algot.graphics.entities.Entity
import com.mist.algot.graphics.models.RawModel
import com.mist.algot.graphics.models.TexturedModel
import com.mist.algot.graphics.rendering.Loader
import com.mist.algot.graphics.textures.ModelTexture
import com.mist.algot.graphics.utils.OBJLoader
import org.lwjgl.util.vector.Vector3f

class ScenariosOld {

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

    static List<Entity> dragons() {
        [
            new Vector3f(0, -5, -25),
            new Vector3f(-300, 100, -800),
            new Vector3f(30, -20, -80)
        ].collect {
            new Entity(DRAGON_MODEL, it)
        }
    }

    static List<Entity> dragonsOverlapFrontFirst() {
        [
            new Vector3f(0, -5, -25),
            new Vector3f(0, 0, -50)
        ].collect {
            new Entity(DRAGON_MODEL, it)
        }
    }

    static List<Entity> dragonsOverlapBackFirst() {
        dragonsOverlapFrontFirst().reverse()
    }

    static List<Entity> trees() {
        def bgTrees = (-10..10).collect {
            [new Vector3f(it*20 as float, (-30-Math.abs(it)) as float, -400),
            new Vector3f(it*25 as float, (-10-Math.abs(it)) as float, -450),
            new Vector3f(it*30 as float, (10-Math.abs(it)) as float, -500)]
        }.flatten() as List<Vector3f>

        (bgTrees + [
            new Vector3f(0, -10, -45),
            new Vector3f(-30, -15, -80),
            new Vector3f(30, -15, -80),
            new Vector3f(-25, -18, -140),
            new Vector3f(25, -18, -140),

            new Vector3f(-130, 20, -300),
            new Vector3f(-110, 25, -310),
            new Vector3f(-90, 20, -300),

            new Vector3f(130, 20, -300),
            new Vector3f(110, 25, -310),
            new Vector3f(90, 20, -300),
        ]).collect {
            new Entity(TREE_MODEL, it)
        }
    }

    static List<Entity> stalls() {
        [
            new Vector3f(0, -5, -25),
            new Vector3f(-30, -3, -50),
            new Vector3f(-15, -3, -50),
            new Vector3f(-0, -3, -50),
            new Vector3f(15, -3, -50),
            new Vector3f(30, -3, -50),
            new Vector3f(-10, -5, -12),
            new Vector3f(10, -5, -12),
        ].collect {
            new Entity(STALL_MODEL, it)
        }
    }

    static List<Entity> stallsNoOverlap() {
        [
            new Vector3f(0, -10, -50),
            new Vector3f(-27, 10, -50),
            new Vector3f(-15, 10, -50),
            new Vector3f(-0, 10, -50),
            new Vector3f(15, 10, -50),
            new Vector3f(27, 10, -50),
            new Vector3f(-15, -10, -50),
            new Vector3f(15, -10, -50),
        ].collect {
            new Entity(STALL_MODEL, it)
        }
    }

    static List<Entity> cube() {
        [
            new Vector3f(0, -1, -7),
        ].collect {
            new Entity(CUBE_MODEL, it)
        }
    }

    private static List<Entity> scenarioFromName(String name) {
        Map<String, Closure<List<Entity>>> scenarios = [
            cube: ScenariosOld.&cube,
            trees: ScenariosOld.&trees,
            stalls: ScenariosOld.&stalls,
            dragons: ScenariosOld.&dragons,
            dragonsFF: ScenariosOld.&dragonsOverlapFrontFirst,
            dragonsBF: ScenariosOld.&dragonsOverlapBackFirst,
            stallsNO: ScenariosOld.&stallsNoOverlap
        ]
        scenarios[name].call()
    }
}
