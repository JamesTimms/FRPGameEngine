package spikeWork;

import junit.framework.TestCase;
import org.FRPengine.core.FRPDisplay;
import sodium.Listener;
import sodium.StreamSink;

/**
 * Created by TekMaTek on 17/02/2015.
 */
public class FRPDisplayTest extends TestCase {

    public void testDisplayWorks() {
        new FRPDisplay().CreateDisplay();
    }

    public void testFRPDisplayWorks() {
        StreamSink<FRPDisplay> displayStream = new StreamSink();
        Listener listener = displayStream.listen(x -> {
            x.CreateDisplay();
        });
        displayStream.send(new FRPDisplay());
    }
}
