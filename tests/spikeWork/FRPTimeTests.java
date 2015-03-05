package spikeWork;

import org.FRPengine.core.FRPDisplay;
import org.FRPengine.core.FRPKeyboard;
import org.junit.After;
import org.junit.Before;
import org.testng.annotations.Test;

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
    public void testDeltaTime() throws InterruptedException {//TODO: Make this work. It's broken...
//        Time testTime = new Time();
//        long delta = 0L;
//        long lastDelta = 0L;
//        testTime.getDeltaTime();
//        int misc = 0;
//        for(int i = 0; i < 100000000; i++) {
//            if(testTime.shouldGetFrame(120 - misc)) {
//                delta = System.nanoTime() - lastDelta;
//                lastDelta = delta;
//                assertTrue(ApproaxEquals(delta/Time.SECOND, testTime.getDeltaTime(), 0));
//                System.out.println("test: " + delta/Time.SECOND + "   real: " + testTime.getDeltaTime());
//                misc ++;
//            }
//        }
    }

    public static boolean ApproaxEquals(long a, long b, int significantFigures) {
        long numberOne = Math.abs(a - b);
        long numberTwo = Math.max(Math.abs(a), Math.abs(b));
        double numberThree = Math.pow(0.1d, significantFigures);
        double numberFour = numberThree
                * numberTwo;
        boolean result = numberOne < numberFour;
        return result;
    }

    @Test
    public void testTimeDelay() {//TODO: Figure out best way to gate time.
//        StreamSink<Time> timeStream = new StreamSink<>();
//        Cell<Boolean> timeGate = timeStream
//                .map(time -> time.shouldGetFrame(Time.THIRTY_PER_SECOND))
//                .hold(false);
//        Cell<Time> timeValue = timeStream
//                .gate( timeGate )
//                .hold(new Time());
//        assertEquals();
    }
}