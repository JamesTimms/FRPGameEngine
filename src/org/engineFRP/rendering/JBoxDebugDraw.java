package org.engineFRP.rendering;

import org.engineFRP.Util.Util;
import org.engineFRP.maths.Vector2f;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.particle.ParticleColor;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by TekMaTek on 07/04/2015.
 */
public class JBoxDebugDraw extends DebugDraw {
    @Override
    public void drawPoint(Vec2 argPoint, float argRadiusOnScreen, Color3f argColor) {
        glColor3f(argColor.x, argColor.y, argColor.z);
        glPointSize(5.0f);
        glBegin(GL_POINTS);
        glVertex2f(argPoint.x, argPoint.y);
        glEnd();
    }

    @Override
    public void drawSolidPolygon(Vec2[] vertices, int vertexCount, Color3f color) {
        glColor3f(color.x, color.y, color.z);
        glBegin(GL_TRIANGLE_STRIP);
        for(Vec2 v : vertices) {
            glVertex2f(v.x, v.y);
        }
        glEnd();
    }

    @Override
    public void drawCircle(Vec2 center, float radius, Color3f color) {
        glColor3f(color.x, color.y, color.z);
        glPointSize(radius);
        glBegin(GL_POINTS);
        glVertex2f(center.x, center.y);
        glEnd();
    }

    @Override
    public void drawSolidCircle(Vec2 center, float radius, Vec2 axis, Color3f color) {
        glColor3f(color.x, color.y, color.z);
        glPointSize(radius);
        glBegin(GL_POINTS);
        glVertex2f(center.x, center.y);
        glEnd();
    }

    @Override
    public void drawSegment(Vec2 p1, Vec2 p2, Color3f color) {
        glColor3f(color.x, color.y, color.z);
        glBegin(GL_LINES);
        Vector2f v1 = Util.vec2ToVector2f(p1);
        Vector2f v2 = Util.vec2ToVector2f(p2);
//        System.out.println(v1 + " " + v2);
        glVertex2f(v1.x, v1.y);
        glVertex2f(v2.x, v2.y);
        glEnd();
    }

    @Override
    public void drawTransform(Transform xf) {
        glColor3f(0.0f, 0.0f, 0.0f);
        glBegin(GL_POINTS);
        glVertex2f(xf.p.x / 10.0f, xf.p.y / 10.0f);
        glEnd();
    }

    @Override
    public void drawString(float x, float y, String s, Color3f color) {
        System.out.println(s);
    }

    @Override
    public void drawParticles(Vec2[] centers, float radius, ParticleColor[] colors, int count) {
        glPointSize(radius);
        glBegin(GL_POINTS);
        for(int i = 0; i < centers.length; i++) {
            glColor3f(colors[i].a, colors[i].g, colors[i].b);
            glVertex2f(centers[i].x, centers[i].y);
        }
        glEnd();
    }

    @Override
    public void drawParticlesWireframe(Vec2[] centers, float radius, ParticleColor[] colors, int count) {
        glPointSize(radius);
        glBegin(GL_POINTS);
        for(int i = 0; i < centers.length; i++) {
            glColor3f(colors[i].a, colors[i].g, colors[i].b);
            glVertex2f(centers[i].x, centers[i].y);
        }
        glEnd();
    }
}
