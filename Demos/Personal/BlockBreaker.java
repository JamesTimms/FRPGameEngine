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
        return Scene.graph.add(
                new Transform(Vector3f.ZERO, MeshUtil.BuildRectWithTexture(BLOCK_TEXTURE, 0.1f, 0.2f))
        );
    }
}