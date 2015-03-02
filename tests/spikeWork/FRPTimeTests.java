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