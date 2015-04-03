package org.engineFRP.FRP;

import org.engineFRP.Util.Util;
import org.engineFRP.core.Transform;
import org.engineFRP.rendering.Mesh;
import org.engineFRP.rendering.Texture;
import org.engineFRP.rendering.shaders.Material;
import org.engineFRP.rendering.shaders.SquareShader;
import org.jbox2d.collision.shapes.PolygonShape;
import sodium.Cell;
import sodium.StreamSink;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;

/**
 * Created by TekMaTek on 03/04/2015.
 */
public class MapOnce<T> {

    T value;

    public MapOnce(T value) {
        this.value = value;
    }

    public <U> MapOnce<U> map(Function<? super T, ? extends U> mapper) {
        U newValue = mapper.apply(value);
        return new MapOnce<U>(newValue);
    }

    public <U> U baseMap(Function<? super T, ? extends U> mapper) {
        return mapper.apply(value);
    }
}