package Personal;

import org.engineFRP.FRP.FRPTime;
import org.engineFRP.FRP.FRPUtil;
import org.engineFRP.FRP.Time;
import org.engineFRP.Util.Node;
import org.engineFRP.core.Engine;
import org.engineFRP.core.Game;
import org.engineFRP.core.GameObject;
import org.engineFRP.core.Scene;
import org.engineFRP.maths.Vector3f;
import org.engineFRP.rendering.MeshUtil;
import sodium.Stream;

/**
 * Created by TekMaTek on 01/03/2015.
 */
public class Demo5 implements Game {

    private static final String TEXT_FILE = "./res/textures/grad.png";

    public static void main(String[] args) {
        Engine.runGame(new Demo5());
    }

    @Override
    public Scene setupScene() {
        return Scene.graph.add(Node.newNode(
                new GameObject(new Vector3f(0.0f, 0.0f, -1.0f), MeshUtil.BuildSquareWithTexture(TEXT_FILE))
                        .mergeTranslationWith(movements())
                        .mergeTranslationWith(FRPUtil.mapArrowKeysToMovementOf(-0.1f))));
    }

    public static Stream<Vector3f> movements() {
        return FRPTime.streamDelta(Time.THIRTY_PER_SECOND)
                .map(deltaTime -> {
                    double curTime = Time.getTime();
                    return new Vector3f((float) Math.sin(curTime) / 80.0f, (float) Math.sin(curTime) / 80.0f, 0.0f);
                });
    }
}