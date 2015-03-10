package Personal;

import org.FRPengine.core.*;
import org.FRPengine.core.collision.AABB;
import org.FRPengine.maths.Vector3f;
import org.FRPengine.rendering.MeshUtil;
import org.FRPengine.rendering.SimpleRenderer;
import org.FRPengine.rendering.shaders.BasicShader;
import org.FRPengine.rendering.shaders.Shader;
import sodium.Stream;
import sodium.StreamSink;
import sodium.Tuple2;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

/**
 * Created by TekMaTek on 01/03/2015.
 */
public class Demo3 {

    public static void main(String[] args) {
        new Demo3();
    }

    static Time renderTimer = new Time();
    static Time pollTimer = new Time();
    static StreamSink<Integer> frameStream = new StreamSink<>();
    static StreamSink<Tuple2<AABB, AABB>> colliderStream;

    static Shader shader2;
    private static Transform[] sceneMeshes;
    private static AABB background;

    public Demo3() {
        FRPDisplay.create();
        FRPKeyboard.create();
        SimpleRenderer.init();
        loop();
    }

    public void setupScene() {
        background = FRPDisplay.setupScreenCollider();
        for(Transform transform : sceneMeshes) {
            transform.mergeIntoCellAndAccum(movements());
            transform.mergeIntoCellAndAccum(mapCollision(transform));
        }
    }

    public static Stream<Vector3f> movements() {
        return Time.deltaOf(frameStream)
                .map(deltaTime -> new Vector3f(0.1f * deltaTime, 0.0f, 0.0f))
                .merge(FRPUtil.mapArrowKeysToMovementOf(-0.1f));
    }

    public static Stream<Vector3f> mapCollision(Transform transform) {
        return colliderStream
                .filter(thing -> (thing.a == transform.collider) || (thing.b == transform.collider))
                .filter(colliders -> colliders.a.isOutsideOf(colliders.b))
                .map(colliders -> (colliders.a.resolveCollision(colliders.b)).mul(-1.0f));
    }

    public static void checkCollisions() {
        colliderStream.send(new Tuple2<>(background, sceneMeshes[1].collider));
    }

    public void loop() {
        colliderStream = new StreamSink<>();
        shader2 = new BasicShader();
        sceneMeshes = new Transform[] {
                new Transform(new Vector3f(0.0f, 0.0f, -1.0f), MeshUtil.BuildSquare()),
                new Transform(new Vector3f(-0.3f, 0.0f, -1.0f), MeshUtil.BuildSquare()),
                new Transform(new Vector3f(0.1f, 0.4f, -1.0f), MeshUtil.BuildSquare()),
                new Transform(new Vector3f(0.3f, 0.0f, -1.0f), MeshUtil.BuildSquare())
        };
        setupScene();

        while(!FRPDisplay.shouldWindowClose()) {
            if(pollTimer.shouldGetFrame(120)) {
                glfwPollEvents();
                checkCollisions();
                if(renderTimer.shouldGetFrame(Time.THIRTY_PER_SECOND)) {
                    renderDemo3();
                }
                frameStream.send(Time.THIRTY_PER_SECOND);//Sent arbitrarily and doesn't matter when it's sent just as long as it
                //isn't infrequently enough to cause a frame to be missed.
            }
        }
    }

    public static void drawDemo3() {
        for(Transform transform : sceneMeshes) {
            shader2.draw(transform);
        }
    }

    public void renderDemo3() {
        glClear(GL_COLOR_BUFFER_BIT);
        drawDemo3();
        glfwSwapBuffers(FRPDisplay.getWindow());
    }
}