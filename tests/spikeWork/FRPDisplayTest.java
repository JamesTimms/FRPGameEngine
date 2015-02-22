package spikeWork;

import org.FRPengine.core.FRPDisplay;
import org.junit.Test;
import sodium.Cell;
import sodium.Listener;
import sodium.StreamSink;

/**
 * Created by TekMaTek on 17/02/2015.
 */
public class FRPDisplayTest {

    @Test
    public void testFRPDisplayWorks() {
        createAndDestroyDisplay();
        createAndDestroyDisplay();
        createAndDestroyDisplay();
    }

    public void createAndDestroyDisplay() {
        StreamSink<FRPDisplay> displayStream = new StreamSink();
        Cell<FRPDisplay> display = displayStream.hold(new FRPDisplay());
        Listener listener = displayStream.listen(x -> {
            x.CreateDisplay();
        });
        displayStream.send(display.sample());
        listener.unlisten();
        Listener destroyListener = displayStream.listen(x -> {
            x.DestroyDisplay();
        });
        displayStream.send(display.sample());
        destroyListener.unlisten();
    }
}