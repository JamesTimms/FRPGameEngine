package Personal;

import org.engineFRP.FRP.FRPTime;
import org.engineFRP.FRP.FRPUtil;
import org.engineFRP.Util.MapUtil;
import org.engineFRP.Util.Util;
import org.engineFRP.core.Engine;
import org.engineFRP.core.Game;
import org.engineFRP.core.Scene;
import org.engineFRP.core.Transform;
import org.engineFRP.rendering.Mesh;
import org.engineFRP.rendering.Texture;
import org.engineFRP.rendering.shaders.Material;
import org.engineFRP.rendering.shaders.SquareShader;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import sodium.Cell;
import sodium.StreamSink;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;

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
        Vec2 gravity = new Vec2(0.0f, -9.8f);
        World theWorld = new World(gravity);

        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(0.0f, -7.0f);
        Body groundBody = theWorld.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(5.0f, 1.0f);
        groundBody.createFixture(groundBox, 0.0f);
        Scene.graph.add(
                MapUtil.polyToTrans(groundBox)
                        .translation(Util.vec2ToScaledVector3f(groundBody.getPosition()))
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

//        StreamSink<Body> bodyStream = new StreamSink<>();
//        bodyStream
//                .map(b -> {
//                            Vertex[] verts = Util.polyToVertexArray(dynamicBox);
//                            Mesh newMesh = new Mesh(verts, Util.genIndices(verts.length), Texture.loadTexture(BLOCK_TEXTURE), false, new SquareShader(GL_TRIANGLE_STRIP));
//                            return new Transform(Util.vec2ToScaledVector3f(b.getPosition())
//                                    , newMesh);
//                        }
//                ).hold(null);

        StreamSink<PolygonShape> dTrans = new StreamSink<>();
        Cell<Transform> trans2 = dTrans
                .once()
                .map(Util::polyToVertexArray)
                .map(verts -> new Mesh(verts, Util.genIndices(verts.length), Texture.NoTexture(), false, new SquareShader(GL_TRIANGLE_FAN)))
                .map(mesh -> new Transform(Util.vec2ToScaledVector3f(body.getPosition()), mesh, Material.blue))
                .hold(null);
        dTrans.send(dynamicBox);
        Transform block = trans2.sample()
                .changeTranslationType(FRPUtil.setVector)
                .mergeTranslation(
                        FRPTime.streamDelta(60)
                                .map(delta -> {
                                    theWorld.step(delta, 6, 2);
//                            return null;
                                    return body.getPosition();
                                })
                                .map(Util::vec2ToScaledVector3f)
                );
        Scene.graph.add(block);
//        float timeStep = 1.0f / 60.0f;
//        int velocityIterations = 6;
//        int positionIterations = 2;
//
//        for(int i = 0; i < 60; i++) {
//            theWorld.step(timeStep, velocityIterations, positionIterations);
//            Vec2 position = body.getPosition();
//            float angle = body.getAngle();
//            System.out.println(position + " " + angle);
//        }
        return Scene.graph;
    }
}