package spikeWork;

import org.FRPengine.core.Time;
import org.junit.After;
import org.junit.Test;
import sodium.Listener;
import sodium.StreamSink;
import sodium.Tuple2;

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

    public static boolean ApproxEqualsRange(float a, float b, float range) {
        return (a + range) > b && (a - range) < b;
    }

    public static boolean ApproaxEquals(float a, float b, int significantFigures) {
        float difference = Math.abs(a - b);
        float max = Math.max(Math.abs(a), Math.abs(b));
        double sigfigs = Math.pow(0.1d, significantFigures);
        double margin = sigfigs
                * max;
        boolean result = difference < margin;
        return result;
    }

    @Test
    public void testTimeDelay() {
        StreamSink<Integer> timeStream = new StreamSink<>();
        final long[] delta = {0L};
        final long[] lastTime = {System.nanoTime()};
        final long[] timeNT = {0l};
        Listener timeListener = timeStream
                .snapshot(Time.stream(), (Integer x, Time z) -> new Tuple2<>(x, z))
                .filter(tuple -> tuple.b.shouldGetFrame(tuple.a))
                .map(tuple -> tuple.b)
                .map(Time::deltaOfFrameRate)
                .listen(time -> {
                    timeNT[0] = System.nanoTime();
                    delta[0] = timeNT[0] - lastTime[0];
                    lastTime[0] = timeNT[0];
//                    System.out.println("test: " + (float) delta[0] / Time.SECOND + "   real: " + time);
                    assertTrue(ApproaxEquals((float) delta[0] / Time.SECOND, time, 2));
                });
        for(int i = 0; i < 1000000; i++) {
            timeStream.send(Time.THIRTY_PER_SECOND);
        }
        timeListener.unlisten();
    }
    //TODO: Test how long it takes to execute the code over imperative code.
}