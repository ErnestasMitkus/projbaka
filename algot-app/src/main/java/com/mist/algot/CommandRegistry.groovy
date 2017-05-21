package com.mist.algot

import com.mist.algot.graphics.controls.KeyboardManager
import com.mist.algot.graphics.entities.Camera
import com.mist.algot.graphics.rendering.HidingManager

import static com.mist.algot.graphics.controls.KeyboardManager.EventType.PRESSED
import static org.lwjgl.input.Keyboard.KEY_B
import static org.lwjgl.input.Keyboard.KEY_NUMPAD1
import static org.lwjgl.input.Keyboard.KEY_NUMPAD2
import static org.lwjgl.input.Keyboard.KEY_NUMPAD3
import static org.lwjgl.input.Keyboard.KEY_NUMPAD4

class CommandRegistry {

    static void registerCommands(Camera camera = null) {
        onPress(KEY_NUMPAD1) {
            HidingManager.backFaceCullingEnabled ? HidingManager.disableBackFaceCulling() : HidingManager.enableBackFaceCulling()
            println "Backface culling enabled: $HidingManager.backFaceCullingEnabled"
        }

        onPress(KEY_NUMPAD2) {
            HidingManager.ZBufferEnabled ? HidingManager.disableZBuffer() : HidingManager.enableZBuffer()
            println "Z Buffer enabled: $HidingManager.ZBufferEnabled"
        }

        onPress(KEY_NUMPAD3) {
            HidingManager.paintersEnabled ? HidingManager.disablePainters() : HidingManager.enablePainters()
            println "Painters enabled: $HidingManager.paintersEnabled"
        }

        onPress(KEY_NUMPAD4) {
            HidingManager.frustumCullingEnabled ? HidingManager.disableFrustumCulling() : HidingManager.enableFrustumCulling()
            println "Frustum culling enabled: $HidingManager.frustumCullingEnabled"
        }

        if (camera) {
            onPress(KEY_B, camera.&goToBirdView)
        }
    }

    private static onPress(int keycode, Closure closure) {
        KeyboardManager.register(keycode, {
            if (it == PRESSED) {
                closure.call()
            }
        })
    }
}
