package Personal;

import org.engineFRP.FRP.FRPUtil;
import org.engineFRP.Util.Node;
import org.engineFRP.Util.Util;
import org.engineFRP.core.Engine;
import org.engineFRP.core.Game;
import org.engineFRP.core.Scene;
import org.engineFRP.core.Transform;
import org.engineFRP.maths.Vector2f;
import org.engineFRP.maths.Vector3f;
import org.engineFRP.rendering.Mesh;
import org.engineFRP.rendering.MeshUtil;
import org.engineFRP.rendering.Texture;
import org.engineFRP.rendering.Vertex;
import org.engineFRP.rendering.shaders.Material;
import org.engineFRP.rendering.shaders.SquareShader;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import sodium.Cell;
import sodium.StreamSink;

/**
 * Created by TekMaTek on 01/03/2015.
 */
public class JBoxDemo implements Game {

    private static final String BLOCK_TEXTURE = "./res/textures/block.png";

    public static void main(String[] args) {
        Engine.runGame(new JBoxDemo());
    }

    @Override
    public Scene setupScene() {
        Transform block = new Transform(new Vector3f(0.0f, -0.90f, -1.0f), MeshUtil.BuildRectWithTexture(BLOCK_TEXTURE, 0.2f, 0.4f), Material.blue);
        block.changeTranslationType(FRPUtil.setVector);
        StreamSink<Vec2> updater = new StreamSink<>();
        block.mergeTranslation(updater
                        .map(Util::vec2ToScaledVector3f)
        );
        Scene.graph.add(new Node<>(block));

        Vec2 gravity = new Vec2(0.0f, -9.8f);
        World theWorld = new World(gravity);

        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(0.0f, -10.0f);
        Body groundBody = theWorld.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(50.0f, 10.0f);
        groundBody.createFixture(groundBox, 0.0f);
        StreamSink<PolygonShape> groundTrans = new StreamSink<>();
        Cell<Transform> trans = groundTrans
                .once()
                .map(Util::polyToVertexArray)
                .map(verts -> new Mesh(verts, Util.genIndicies(verts.length), Texture.NoTexture(), false, new SquareShader()))
                .map(mesh -> new Transform(Util.vec2ToScaledVector3f(groundBodyDef.position), mesh, Material.white))
                .hold(null);
        groundTrans.send(groundBox);
        Scene.graph.add(
                trans.sample()
        );

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(0.0f, 4.0f);
        bodyDef.fixedRotation = true;
        Body body = theWorld.createBody(bodyDef);
        PolygonShape dynamicBox = new PolygonShape();
        dynamicBox.setAsBox(1.0f, 1.0f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = dynamicBox;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.3f;
        body.createFixture(fixtureDef);

        float timeStep = 1.0f / 60.0f;
        int velocityIterations = 6;
        int positionIterations = 2;

        for(int i = 0; i < 60; i++) {
            theWorld.step(timeStep, velocityIterations, positionIterations);
            Vec2 position = body.getPosition();
            updater.send(position);
            float angle = body.getAngle();
            System.out.println(position + " " + angle);
        }
        return Scene.graph;
    }
}