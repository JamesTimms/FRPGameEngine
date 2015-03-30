package Personal;

import org.engineFRP.core.Engine;
import org.engineFRP.core.Game;
import org.engineFRP.core.Scene;
import org.engineFRP.core.Transform;
import org.engineFRP.maths.Vector3f;
import org.engineFRP.rendering.MeshUtil;

/**
 * Created by TekMaTek on 30/03/2015.
 */
public class BlockBreaker implements Game {

    private static final String BLOCK_TEXTURE = "./res/textures/block.png";

    public static void main(String[] args) {
        Engine.runGame(new BlockBreaker());
    }

    @Override
    public Scene setupScene() {
        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) {
                Scene.graph.add(
                        new Transform(new Vector3f(0.36f * i, 0.13f * j, -1.0f), MeshUtil.BuildRectWithTexture(BLOCK_TEXTURE, 0.2f, 0.4f))
                );
            }
        }
        return Scene.graph;
    }
}