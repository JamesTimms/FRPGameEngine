package spikeWork;

import org.FRPengine.core.FRPDisplay;
import org.FRPengine.core.FRPKeyboard;
import org.FRPengine.core.Time;
import org.junit.After;
import org.junit.Before;
import org.testng.annotations.Test;

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
    public void testDeltaTime() {//TODO: Make this work. It's broken...
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
                System.out.println("test: " + (float)delta/ Time.SECOND + "   real: " + testTime.getDeltaTime());
                assertTrue(ApproxEqualsRange((float)delta/ Time.SECOND, testTime.getDeltaTime(), (float)70000 / Time.SECOND));
//                assertTrue(ApproxEquals((float) delta / Time.SECOND, testTime.getDeltaTime(), 1));
                misc ++;
            }
        }
    }

    public static boolean ApproxEqualsRange(float a, float b, float range){
        return (a + range) > b && (a - range) < b;
    }

    public static boolean ApproxEquals(float a, float b, int significantFigures) {
        float numberOne = Math.abs(a - b);
        float numberTwo = Math.max(Math.abs(a), Math.abs(b));
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