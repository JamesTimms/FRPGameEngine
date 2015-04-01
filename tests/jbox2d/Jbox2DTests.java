package jbox2d;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.junit.Test;

/**
 * Created by TekMaTek on 01/04/2015.
 */
public class Jbox2DTests {

    @Test
    public void testSomething() {
        Vec2 gravity = new Vec2(0.0f, -9.8f);
        World theWorld = new World(gravity);

        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(0.0f, -10.0f);
        Body groundBody = theWorld.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(50.0f, 10.0f);
        groundBody.createFixture(groundBox, 0.0f);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(0.0f, 4.0f);
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
            float angle = body.getAngle();
            System.out.println(position + " " + angle);
        }
    }

}
