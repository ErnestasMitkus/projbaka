package com.mist.algot.graphics.toolbox

import static org.lwjgl.input.Keyboard.KEYBOARD_SIZE
import static org.lwjgl.input.Keyboard.isKeyDown

class Controls {

    private static Boolean[] keys = new boolean[KEYBOARD_SIZE]
    private static Boolean[] shouldTrigger = new boolean[KEYBOARD_SIZE]

    static {
        (0..(KEYBOARD_SIZE - 1)).each {
            shouldTrigger[it] = false
        }
    }

    static void clear() {
        (0..(KEYBOARD_SIZE - 1)).each {
            if (shouldTrigger[it]) {
                keys[it] = isKeyDown(it)
                if (!keys[it]) {
                    shouldTrigger[it] = false
                }
            }
        }
    }

    static boolean isJustPressed(int keycode) {
        boolean keydown = isKeyDown(keycode)
        boolean result = keydown && !keys[keycode]
        if (keydown) {
            keys[keycode] = true
            shouldTrigger[keycode] = true
        }
        return result
    }
}
