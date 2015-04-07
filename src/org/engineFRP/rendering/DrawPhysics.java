package org.engineFRP.rendering;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.particle.ParticleColor;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by TekMaTek on 06/04/2015.
 */
public class DrawPhysics extends DebugDraw {

    @Override
    public void drawPoint(Vec2 argPoint, float argRadiusOnScreen, Color3f argColor) {
        glPointSize(4.0f);
        glColor3i((int) argColor.x, (int) argColor.y, (int) argColor.z);
        glBegin(GL_POINTS);
        glVertex3f(argPoint.x, argPoint.y, 0.0f);
        glVertex3f(argPoint.x, argPoint.y, 0.0f);
        glEnd();
    }

    @Override
    public void drawSolidPolygon(Vec2[] vertices, int vertexCount, Color3f color) {
//        glColor3i((int) color.x, (int) color.y, (int) color.z);
//        glBegin();
//        glEnd();
    }

    @Override
    public void drawCircle(Vec2 center, float radius, Color3f color) {

    }

    @Override
    public void drawSolidCircle(Vec2 center, float radius, Vec2 axis, Color3f color) {

    }

    @Override
    public void drawSegment(Vec2 p1, Vec2 p2, Color3f color) {
        glColor3i((int) color.x, (int) color.y, (int) color.z);
        glBegin(GL_LINE);
        glVertex3f(p1.x, p1.y, 0.0f);
        glVertex3f(p2.x, p2.y, 0.0f);
        glEnd();
    }

    @Override
    public void drawTransform(Transform xf) {

    }

    @Override
    public void drawString(float x, float y, String s, Color3f color) {

    }

    @Override
    public void drawParticles(Vec2[] centers, float radius, ParticleColor[] colors, int count) {

    }

    @Override
    public void drawParticlesWireframe(Vec2[] centers, float radius, ParticleColor[] colors, int count) {

    }
}
