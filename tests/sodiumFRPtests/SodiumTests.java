package sodiumFRPtests;

import org.junit.After;
import org.junit.Test;
import sodium.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests taken from the SodiumFRP test framework.
 */
public class SodiumTests {
    @After
    public void tearDown() throws Exception {
        System.gc();
        Thread.sleep(100);
    }

    @Test
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

    class Position {
        Position(Integer a, Integer b) {
            this.a = a;
            this.b = b;
        }

        Integer a;
        Integer b;

        public boolean equals(Position obj) {
            return (obj.a == this.a) && (obj.b == this.b);
        }
    }

    @Test
    public void testInitialPositionCell() {
        StreamSink<Position> posStream = new StreamSink();
        Position expectedPos = new Position(1, 1);
        Cell<Position> actualValue = posStream.hold(expectedPos);
        assertEquals(expectedPos, actualValue.sample());
    }

    @Test
    public void testPositionCell() {
        StreamSink<Position> posStream = new StreamSink();
        Cell<Position> actualValue = posStream.hold(new Position(1, 1));
        posStream.send(new Position(1, 2));
        posStream.send(new Position(3, 4));
        Position expectedPos = new Position(5, 6);
        posStream.send(expectedPos);
        assertEquals(expectedPos, actualValue.sample());
    }

    @Test
    public void testSimplePositionAssignment() {
        StreamSink<Position> posStream = new StreamSink();
        Position initialValue = null;
        Cell<Position> pos = posStream.hold(initialValue);
        posStream.send(new Position(1, 2));
        assertTrue(new Position(1, 2).equals(pos.sample()));
    }

    @Test
    public void testCastFailure() {
        StreamSink<Position> posStream = (StreamSink<Position>) new StreamSink<Position>()
                .filter(position -> position.a != 1);//By casting to StreamSink the filter is lost.
        Cell<Position> pos = posStream.hold(null);
        posStream.send(new Position(1, 2));
        assertTrue(new Position(1, 2).equals(pos.sample()));//This should be null.
    }

    @Test
    public void testValuesTwiceThenMap() {
        StreamSink<Integer> testStream = new StreamSink<>();
        Cell<Integer> testCell = testStream
                .merge(testStream, (f, s) -> f + s)
                .hold(0);
        testStream.send(5);
        assertEquals(10, testCell.sample().intValue());
    }

    @Test
    public void testDoSomethingAmazing() {
        Optional<Stream<Integer>> myCellInt = Optional.of(new Stream<Integer>());
        Optional<Cell<Integer>> cell = myCellInt.map(thing -> thing.hold(5));
        assertEquals(new Integer(5), cell.get().sample());
    }
}
