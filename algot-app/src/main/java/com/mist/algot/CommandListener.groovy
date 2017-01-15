package com.mist.algot

import com.mist.algot.graphics.rendering.HidingManager
import org.lwjgl.input.Keyboard

import static org.lwjgl.input.Keyboard.KEY_NUMPAD1
import static org.lwjgl.input.Keyboard.KEY_NUMPAD2
import static org.lwjgl.input.Keyboard.KEY_NUMPAD3

class CommandListener {

    private final static boolean[] keys = new boolean[Keyboard.KEYBOARD_SIZE]
    private final static boolean[] wasPressed = new boolean[Keyboard.KEYBOARD_SIZE]

    private final static int[] importantKeys = [KEY_NUMPAD1, KEY_NUMPAD2, KEY_NUMPAD3]

    static {
        keys.eachWithIndex { _, int i -> keys[i] = false }
        wasPressed.eachWithIndex { _, int i -> wasPressed[i] = false }
    }

    static void listen() {
        importantKeys.each {
            if (Keyboard.isKeyDown(it)) {
                if (keys[it]) {
                    wasPressed[it] = true
                } else {
                    keys[it] = true
                }
            } else {
                if (keys[it]) {
                    keys[it] = false
                } else {
                    wasPressed[it] = false
                }
            }
        }
        applyCommands()
    }

    static void applyCommands() {
        if (isPressed(KEY_NUMPAD1)) {
            HidingManager.backFaceCullingEnabled ? HidingManager.disableBackFaceCulling() : HidingManager.enableBackFaceCulling()
            println "Backface culling enabled: $HidingManager.backFaceCullingEnabled"
        }

        if (isPressed(KEY_NUMPAD2)) {
            HidingManager.ZBufferEnabled ? HidingManager.disableZBuffer() : HidingManager.enableZBuffer()
            println "Z Buffer enabled: $HidingManager.ZBufferEnabled"
        }

        if (isPressed(KEY_NUMPAD3)) {
            HidingManager.paintersEnabled ? HidingManager.disablePainters() : HidingManager.enablePainters()
            println "Painters enabled: $HidingManager.paintersEnabled"
        }
    }

    static boolean isPressed(int keycode) {
        keys[keycode] && !wasPressed[keycode]
    }

    static boolean isReleased(int keycode) {
        !keys[keycode] && wasPressed[keycode]
    }
}
