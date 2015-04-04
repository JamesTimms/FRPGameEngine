package Personal;

import org.engineFRP.FRP.FRPTime;
import org.engineFRP.FRP.FRPUtil;
import org.engineFRP.FRP.JBoxWrapper;
import org.engineFRP.Util.MapUtil;
import org.engineFRP.Util.Util;
import org.engineFRP.core.Engine;
import org.engineFRP.core.Game;
import org.engineFRP.core.GameObject;
import org.engineFRP.core.Scene;
import org.engineFRP.maths.Vector3f;
import org.engineFRP.rendering.Mesh;
import org.engineFRP.rendering.Texture;
import org.engineFRP.rendering.shaders.SquareShader;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;

/**
 * Created by TekMaTek on 01/03/2015.
 */
public class JBoxDemo implements Game {

    private static final String BOX_TEXTURE = "./res/textures/box.jpg";
    private static final String STONE_TEXTURE = "./res/textures/stone.jpg";

    public static void main(String[] args) {
        Engine.runGame(new JBoxDemo());
    }

    @Override
    public Scene setupScene() {

//        BodyDef groundBodyDef = new BodyDef();
//        groundBodyDef.position.set(0.0f, -7.0f);
//        Body groundBody = theWorld.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(5.0f, 1.0f);
//        groundBody.createFixture(groundBox, 0.0f);//Add to world.

        GameObject trans = MapUtil.polyToTrans(groundBox)
                .translation(new Vector3f(0.0f, -0.7f, 0.0f))
                .addStaticPhysics()
                .mergeTranslation(FRPUtil.mapArrowKeysToMovementOf(-0.01f))
                .updateToJbox();
        trans.mesh.texture = Texture.loadTexture(STONE_TEXTURE)
                .changeSetting(Texture::RepeatTexture);
        Scene.graph.add(trans);


        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(0.0f, 4.0f);
        bodyDef.fixedRotation = false;
        Body body = JBoxWrapper.world.createBody(bodyDef);
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
//                            return new Transform(Util.vec2ToVector3f(b.getPosition())
//                                    , newMesh);
//                        }
//                ).hold(null);
        GameObject dTrans =
                MapUtil.polyToTrans(dynamicBox)
                        .changeTranslationType(FRPUtil.setVector)
                        .mergeTranslation(
                                FRPTime.streamDelta(60)
                                        .map(delta -> body.getPosition())
                                        .map(Util::vec2ToVector3f)
                        )
                        .mergeRotation(
                                FRPTime.streamDelta(60)
                                        .map(delta -> body.getTransform().q.getAngle())
                                        .map(angle -> new Vector3f(0.0f, 0.0f, angle))
                        )
                        .translation(Util.vec2ToVector3f(body.getPosition()));
        dTrans.mesh.texture = Texture.loadTexture(BOX_TEXTURE);
        Scene.graph.add(dTrans);
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