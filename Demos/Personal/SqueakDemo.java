package Personal;

import org.engineFRP.FRP.FRPTime;
import org.engineFRP.FRP.FRPUtil;
import org.engineFRP.FRP.Time;
import org.engineFRP.core.Engine;
import org.engineFRP.core.Game;
import org.engineFRP.core.GameObject;
import org.engineFRP.core.Scene;
import org.engineFRP.maths.Vector3f;
import org.engineFRP.rendering.MeshUtil;
import sodium.Stream;

/**
 * Created by TekMaTek on 09/04/2015.
 */
public class SqueakDemo implements Game {

    private static final String SQUEAK = "./res/squeak.png";
    private static final Vector3f TWICE_THE_SIZE = new Vector3f(2.0f, 2.0f, 2.0f);
    private static final Stream<Vector3f> ARROW_KEY_MOVEMENT = FRPUtil.mapArrowKeysToMovementOf(-0.1f);
    private static final Stream<Vector3f> SQUEAKS_MOVEMENT = FRPTime.streamDelta(Time.THIRTY_PER_SECOND)
            .accum(0.0f, (total, delta) -> total += delta)
            .updates()
            .map(total -> new Vector3f((float) Math.cos(total) / 80.0f,
                    (float) Math.sin(total) / 80.0f, 0.0f));

    public static void main(String[] args) {
        Engine.runGame(new SqueakDemo());
    }

    @Override
    public Scene setupScene() {
        return Scene.graph.add(
                new GameObject(new Vector3f(0.0f, 0.0f, -1.0f), MeshUtil.BuildSquareWithTexture(SQUEAK))
                        .name("Squeak")
                        .scale(TWICE_THE_SIZE)
                        .mergeTranslationWith(ARROW_KEY_MOVEMENT)
                        .mergeTranslationWith(SQUEAKS_MOVEMENT)
        );
    }
}