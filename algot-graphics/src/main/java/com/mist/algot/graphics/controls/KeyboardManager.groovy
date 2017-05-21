package com.mist.algot.graphics.controls

import com.google.common.base.Preconditions
import com.google.common.collect.Lists

import static org.lwjgl.input.Keyboard.KEYBOARD_SIZE
import static org.lwjgl.input.Keyboard.isKeyDown

class KeyboardManager {

    private static final boolean[] keys = new boolean[KEYBOARD_SIZE]
    private static final boolean[] prevKeys = new boolean[KEYBOARD_SIZE]

    private static final Map<Integer, LinkedList<EventListener>> listeners = [:]

    static void process() {
        listeners.keySet().each {
            prevKeys[it] = keys[it] ?: false
            keys[it] = isKeyDown(it)

            EventType eventType = determineEventType(keys[it], prevKeys[it])
            if (eventType) {
                for (def listener: listeners[it]) {
                    if (listener.process(eventType)) {
                        break
                    }
                }
            }
        }
    }

    static void register(int keycode, EventListener listener) {
        Preconditions.checkArgument(0 <= keycode && keycode < KEYBOARD_SIZE)
        if (!listeners.keySet().contains(keycode)) {
            prevKeys[keycode] = false
            keys[keycode] = isKeyDown(keycode)
        }
        if (!listeners[keycode]) {
            listeners[keycode] = Lists.newLinkedList()
        }
        listeners[keycode].add(0, listener)
    }

    static void onPress(int keycode, Closure closure) {
        register(keycode, {
            if (it == EventType.PRESSED) {
                closure.call()
            }
        })
    }

    private static EventType determineEventType(boolean now, boolean prev) {
        if (now && prev) {
            return EventType.HOLD
        } else if (now) {
            return EventType.PRESSED
        } else if (prev) {
            return EventType.RELEASED
        }
        return null
    }

    static enum EventType {
        PRESSED, HOLD, RELEASED
    }

    static interface EventListener {
        /**
         * @returns if the event should stop propagating, null == false
         * */
        Boolean process(EventType type)
    }
}
