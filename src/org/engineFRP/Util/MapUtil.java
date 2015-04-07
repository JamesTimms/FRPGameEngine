package org.engineFRP.Util;

import org.engineFRP.FRP.Mapper;
import org.engineFRP.core.GameObject;
import org.engineFRP.maths.Vector3f;
import org.engineFRP.rendering.Mesh;
import org.engineFRP.rendering.Texture;
import org.engineFRP.rendering.shaders.Material;
import org.engineFRP.rendering.shaders.SquareShader;
import org.jbox2d.collision.shapes.PolygonShape;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;

/**
 * Created by TekMaTek on 03/04/2015.
 */
public class MapUtil {

    /**
     * Takes in a JBox2D PolygonShape and returns our own Transform object.
     * This creates an association between the physics and the graphical components of the engine.
     *
     * @param poly JBox2D PolygonShape that defines the AABB and other physics collision/resolution stuff.
     * @return FRPEngine Transform used for rendering and other non-physics stuff.
     */
    public static GameObject polyToGameObject(PolygonShape poly) {
        return new Mapper<>(poly).map(Util::polyToVertexArray)
                .map(verts -> new Mesh(verts, Util.genIndices(verts.length), Texture.NoTexture(), false, new SquareShader(GL_TRIANGLE_FAN, 0.25f, 0.5f)))
                .baseMap(mesh -> new GameObject(Vector3f.ZERO, mesh, Material.white));
    }
}