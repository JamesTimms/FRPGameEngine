package spikeWork;

import org.engineFRP.FRP.FRPDisplay;
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
            FRPDisplay.createForTesting();
        });
        displayStream.send(display.sample());
        listener.unlisten();
        Listener destroyListener = displayStream.listen(x -> {
            FRPDisplay.destroy();
        });
        displayStream.send(display.sample());
        destroyListener.unlisten();
    }
}