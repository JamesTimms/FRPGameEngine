package spikeWork;

import org.FRPengine.core.FRPDisplay;
import org.FRPengine.core.FRPKeyboard;
import org.FRPengine.core.Time;
import org.junit.After;
import org.junit.Before;
import org.testng.annotations.Test;
import sodium.Listener;
import sodium.StreamSink;

import static junit.framework.Assert.assertTrue;
//

/**
 * Created by TekMaTek on 02/03/2015.
 */
public class FRPTimeTests {

    @Before
    public void createDisplayAndKeyboard() {
        FRPDisplay.Create();
        FRPKeyboard.Create();
    }

    @After
    public void destroyDisplayAndKeyboard() throws Exception {
        FRPKeyboard.Destroy();
        FRPDisplay.Destroy();
        System.gc();
        Thread.sleep(100);
    }

    @Test
    public void testDeltaTime() {
        Time testTime = new Time();
        long delta = 0L;
        long lastTime = System.nanoTime();
        testTime.getDeltaTime();
        int misc = 0;
        for(int i = 0; i < 10000000; i++) {
            if(testTime.shouldGetFrame(120 - misc)) {
                long time = System.nanoTime();
                delta = time - lastTime;
                lastTime = time;
//                System.out.println("test: " + (float) delta / Time.SECOND + "   real: " + testTime.getDeltaTime());
                assertTrue(ApproxEqualsRange((float) delta / Time.SECOND, testTime.getDeltaTime(), (float) 70000 / Time.SECOND));
                misc++;
            }
        }
    }

    public static boolean ApproxEqualsRange(float a, float b, float range) {
        return (a + range) > b && (a - range) < b;
    }

    @Test
    public void testTimeDelay() {//TODO: Sometimes fails an assert but somehow still passes.
        StreamSink<Time> timeStream = new StreamSink<>();
        Time testTime = new Time();
        final long[] delta = {0L};
        final long[] lastTime = {System.nanoTime()};
        final long[] timeNT = {0l};
        Listener timeListener = timeStream
                .filter(time -> time.shouldGetFrame(Time.THIRTY_PER_SECOND))
                .listen(time -> {
                    timeNT[0] = System.nanoTime();
                    delta[0] = timeNT[0] - lastTime[0];
                    lastTime[0] = timeNT[0];
//                    System.out.println("test: " + (float) delta[0] / Time.SECOND + "   real: " + testTime.getDeltaTime());
                    assertTrue(ApproxEqualsRange((float) delta[0] / Time.SECOND, testTime.getDeltaTime(), (float) 70000 / Time.SECOND));
                });
        for(int i = 0; i < 10000000; i++) {
            timeStream.send(testTime);
        }
        timeListener.unlisten();
    }
}