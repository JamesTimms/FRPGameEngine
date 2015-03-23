package Personal;

import org.engineFRP.FRP.FRPTime;
import org.engineFRP.FRP.Time;
import org.engineFRP.core.Engine;
import org.engineFRP.core.Game;
import org.engineFRP.core.Scene;
import org.engineFRP.maths.Vector3f;
import sodium.Stream;

/**
 * Created by TekMaTek on 01/03/2015.
 */
public class Demo5 implements Game {

    private static final String TEXT_FILE = "./res/textures/grad.png";


    public static void main(String[] args) {
//        try {
//            Engine.StartEngine(new Demo5());
//        } catch(Exception e) {
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//        }
        Engine.runGame(new Demo5());
    }

    public static Stream<Vector3f> movements() {
        return FRPTime.streamDelta(Time.THIRTY_PER_SECOND)
                .map(deltaTime -> {
                    double curTime = Time.getTime();
                    return new Vector3f((float) Math.sin(curTime) / 80.0f, (float) Math.sin(curTime) / 80.0f, 0.0f);
                });
    }

    @Override
    public Scene setupScene() {
        return Scene.graph;
//        return new Scene(
//                new Transform[] {
//                        new Transform(new Vector3f(0.0f, 0.0f, -1.0f), MeshUtil.BuildSquareWithTexture(TEXT_FILE))
//                                .mergeTranslation(movements())
//                                .mergeTranslation(FRPUtil.mapArrowKeysToMovementOf(-0.1f))
//                }
//        );
    }
}