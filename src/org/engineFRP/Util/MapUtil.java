package org.engineFRP.Util;

import org.engineFRP.FRP.MapOnce;
import org.engineFRP.core.Transform;
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

    public static Transform polyToTrans(PolygonShape poly) {
        return new MapOnce<>(poly).map(Util::polyToVertexArray)
                .map(verts -> new Mesh(verts, Util.genIndices(verts.length), Texture.NoTexture(), false, new SquareShader(GL_TRIANGLE_FAN)))
                .baseMap(mesh -> new Transform(Vector3f.ZERO, mesh, Material.green));
    }
}