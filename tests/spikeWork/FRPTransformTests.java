package spikeWork;

import org.FRPengine.core.Transform;
import org.FRPengine.maths.Vector3f;
import org.junit.After;
import org.testng.annotations.Test;
import sodium.StreamSink;

import static org.testng.Assert.assertTrue;

/**
 * Created by TekMaTek on 08/03/2015.
 */
public class FRPTransformTests {

    @After
    public void destroyDisplayAndKeyboard() throws Exception {
        System.gc();
        Thread.sleep(100);
    }

    @Test
    public void testMergeIntoCellAndAccum() {
        Transform testTransform = new Transform();
        StreamSink<Vector3f> moveStream = new StreamSink<>();

        testTransform.mergeIntoCellAndAccum(moveStream);
        moveStream.send(Vector3f.ONE);
        assertTrue(Vector3f.ONE.equals(testTransform.translation.sample()));

        moveStream.send(Vector3f.ONE);
        assertTrue(new Vector3f(2.0f, 2.0f, 2.0f).equals(testTransform.translation.sample()));
    }

    @Test
    public void testMergeIntoCellAndAccum2() {
        Transform testTransform = new Transform();
        StreamSink<Vector3f> moveStream = new StreamSink<>();
        StreamSink<Vector3f> moveStream2 = new StreamSink<>();

        testTransform.mergeIntoCellAndAccum(moveStream);
        testTransform.mergeIntoCellAndAccum(moveStream2);
        moveStream.send(Vector3f.ONE);
        moveStream2.send(Vector3f.ONE);
        Vector3f eads= testTransform.translation.sample();
        assertTrue(new Vector3f(2.0f, 2.0f, 2.0f).equals(eads));

        moveStream.send(Vector3f.ONE);
        eads= testTransform.translation.sample();
        assertTrue(new Vector3f(3.0f, 3.0f, 3.0f).equals(eads));

        moveStream.send(Vector3f.ONE);
        moveStream.send(Vector3f.ONE);
        moveStream2.send(Vector3f.ONE);
        moveStream.send(Vector3f.ONE);
        moveStream2.send(Vector3f.ONE);
        eads= testTransform.translation.sample();
        assertTrue(new Vector3f(8.0f, 8.0f, 8.0f).equals(eads));
    }
}
