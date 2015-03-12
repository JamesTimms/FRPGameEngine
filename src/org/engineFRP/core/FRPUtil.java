package org.engineFRP.core;

import org.engineFRP.maths.Vector3f;
import sodium.Stream;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by TekMaTek on 03/03/2015.
 */
public class FRPUtil {

    public static Stream<Vector3f> mapArrowKeysToMovementOf(float moveAmount) {
        return FRPKeyboard.keyEvent
                .filter(key -> key.action != GLFW_RELEASE
                        && FRPKeyboard.isArrowKeyPressed(key.key))
                .map(key -> {
                    switch(key.key) {
                        case (GLFW_KEY_RIGHT):
                            return new Vector3f(-moveAmount, 0.0f, 0.0f);
                        case (GLFW_KEY_LEFT):
                            return new Vector3f(moveAmount, 0.0f, 0.0f);
                        case (GLFW_KEY_UP):
                            return new Vector3f(0.0f, -moveAmount, 0.0f);
                        case (GLFW_KEY_DOWN):
                            return new Vector3f(0.0f, moveAmount, 0.0f);
                        default:
                            return Vector3f.ZERO;
                    }
                });
    }
}