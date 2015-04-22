package org.engineFRP.FRP;

import org.engineFRP.maths.Vector3f;
import sodium.Cell;
import sodium.Lambda2;
import sodium.Stream;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by TekMaTek on 03/03/2015.
 */
public class FRPUtil {

    public static final Lambda2<Cell<Vector3f>, Stream<Vector3f>, Cell<Vector3f>> addVectors =
            (cell, stream) -> stream
                    .accum(cell.sample(), (a, b) -> a.add(b));

    public static final Lambda2<Cell<Vector3f>, Stream<Vector3f>, Cell<Vector3f>> setVector =
            (cell, stream) -> stream
                    .hold(cell.sample());

    public static final Stream<Vector3f> mapArrowKeysToMovementOf(float moveAmount) {
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
                            assert false;//This shouldn't be called.
                            return Vector3f.ZERO;
                    }
                });
    }
}