package sodiumFRPtests;

import junit.framework.TestCase;
import sodium.Cell;
import sodium.CellSink;
import sodium.Listener;
import sodium.StreamSink;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by TekMaTek on 16/02/2015.
 */
public class SodiumTests extends TestCase {
    @Override
    protected void tearDown() throws Exception {
        System.gc();
        Thread.sleep(100);
    }

    public void testValues() {
        CellSink<Integer> b = new CellSink<Integer>(19);
        List<Integer> out = new ArrayList<Integer>();
        Listener l = b.listen(x -> {
            out.add(x);
        });
        b.send(2);
        b.send(7);
        l.unlisten();
        assertEquals(Arrays.asList(19, 2, 7), out);
    }

    public void testMultiListenerValues() {
        CellSink<Integer> b = new CellSink<Integer>(19);
        List<Integer> out = new ArrayList<Integer>();
        Listener l = b.listen(x -> {
            out.add(x);
        });
        Listener l2 = b.listen(x -> {
            out.add(x);
        });
        b.send(2);
        b.send(7);
        l.unlisten();
        l2.unlisten();
        assertEquals(Arrays.asList(19, 19, 2, 2, 7, 7), out);
    }

    public void testConstantBehavior() {
        Cell<Integer> b = new Cell<Integer>(12);
        List<Integer> out = new ArrayList();
        Listener l = b.listen(x -> {
            out.add(x);
        });
        l.unlisten();
        assertEquals(Arrays.asList(12), out);
    }

    public void testValuesThenMap() {
        CellSink<Integer> b = new CellSink<Integer>(9);
        List<Integer> out = new ArrayList<Integer>();
        Listener l = b.value().map(x -> x + 100).listen(x -> {
            out.add(x);
        });
        b.send(2);
        b.send(7);
        l.unlisten();
        assertEquals(Arrays.asList(109, 102, 107), out);
    }

    public void testMergeSimultaneous() {
        StreamSink<Integer> e = new StreamSink();
        List<Integer> out = new ArrayList();
        Listener l = e.merge(e).listen(x -> {
            out.add(x);
        });
        e.send(7);
        e.send(9);
        l.unlisten();
        assertEquals(Arrays.asList(7, 7, 9, 9), out);
    }

    public void testMergeSimultaneous2() {
        StreamSink<Integer> e = new StreamSink();
        List<Integer> out = new ArrayList();
        //stream on the left below is done first. Then stream on the right is done second.
        Listener l = e.map(x -> x + 1).merge(e).listen(x -> {
            out.add(x);
        });
        e.send(7);
        e.send(9);
        l.unlisten();
        assertEquals(Arrays.asList(8, 7, 10, 9), out);
    }
}
