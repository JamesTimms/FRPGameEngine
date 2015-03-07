package spikeWork;

import org.FRPengine.core.Time;
import org.junit.After;
import org.junit.Test;
import sodium.Listener;
import sodium.StreamSink;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;
//

/**
 * Created by TekMaTek on 02/03/2015.
 */
public class FRPTimeTests {

    @After
    public void destroyDisplayAndKeyboard() throws Exception {
        System.gc();
        Thread.sleep(100);
    }

    public static boolean ApproxEqualsRange(float a, double b, double range) {
        return (a + range) > b && (a - range) < b;
    }

    public static boolean ApproxEquals(float a, float b, int significantFigures) {
        float difference = Math.abs(a - b);
        float max = Math.max(Math.abs(a), Math.abs(b));
        double sigfigs = Math.pow(0.1d, significantFigures);
        double margin = sigfigs * max;
        /*Sometimes the most significant digit is off by 1. In that case we want to test
        that it is ONLY off by 1 because if it is that's good enough.*/
        boolean result = difference < margin || ApproxEqualsRange(difference, margin, 1.0f * sigfigs);
        return result;
    }

    @Test
    public void testTimeDelay() {
        StreamSink<Integer> timeStream = new StreamSink<>();
        final long[] delta = {0L};
        final long[] lastTime = {System.nanoTime()};
        final long[] timeNT = {0l};
        Listener timeListener = Time.deltaOf(timeStream)
                .listen(time -> {
                    timeNT[0] = System.nanoTime();
                    delta[0] = timeNT[0] - lastTime[0];
                    lastTime[0] = timeNT[0];
//                    System.out.println("test: " + (float) delta[0] / Time.SECOND + "   real: " + time);
                    assertTrue(ApproxEquals((float) delta[0] / Time.SECOND, time, 2));
                });
        for(int i = 0; i < 1000000; i++) {
            timeStream.send(Time.THIRTY_PER_SECOND);
        }
        timeListener.unlisten();
    }

    @Test
    public void testTimeInstantly() {
        StreamSink<Integer> timeStream = new StreamSink<>();
        final int[] count = {0};
        Listener timeListener = Time.deltaOf(timeStream)
                .listen(time -> count[0]++);
        for(int i = 0; i < 100; i++) {
            timeStream.send(Time.INSTANTLY);
        }
        assertEquals(100, count[0]);
        timeListener.unlisten();
    }
    //TODO: Test how long it takes to execute the code over imperative code.
}