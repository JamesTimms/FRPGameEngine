package org.engineFRP.core;

import sodium.Cell;
import sodium.Stream;
import sodium.Tuple2;

/**
 * Created by TekMaTek on 21/03/2014.
 */
public class Time {

    public static final int FRAME_CAP = 120;
    public static final int INSTANTLY = 0;
    public static final int ONE_PER_SECOND = 1;
    public static final int TEN_PER_SECOND = 10;
    public static final int THIRTY_PER_SECOND = 30;
    public static final long SECOND = 1000000000L;

    Long perLoopDelta;
    Double timeElapsed;
    Long timeLastLoop;
    Long timeThisLoop;

    Long previousDeltaTime;
    Long deltaTime;

    public Time() {
        perLoopDelta = 0l;
        timeElapsed = 0.0d;
        timeLastLoop = 0l;
        timeThisLoop = 0l;
        previousDeltaTime = getTime();
        deltaTime = 0l;
    }

    public static long getTime() {
        return System.nanoTime();
    }

    public boolean shouldGetFrame(int frameRate) {
        timeThisLoop = getTime();
        perLoopDelta = timeThisLoop - timeLastLoop;
        timeLastLoop = timeThisLoop;
        long _frameRate = (frameRate > FRAME_CAP) ? FRAME_CAP : frameRate;
        long frameThrottle = (_frameRate <= 0) ? 0 : SECOND / _frameRate;
        timeElapsed = timeElapsed + perLoopDelta;
        boolean isReady = timeElapsed > frameThrottle;
        if(isReady) {
            timeElapsed = 0.0d;
            deltaTime = timeThisLoop - previousDeltaTime;
            previousDeltaTime = timeThisLoop;
        }
        return isReady;
    }

    public static Cell<Time> stream() {
        return new Stream<Time>()
                .hold(new Time());
    }

    public static Stream<Time> stream(Stream<Integer> frameRateStream) {
        return frameRateStream
                .snapshot(Time.stream(), (Integer x, Time z) -> new Tuple2<>(x, z))
                .filter(tuple -> tuple.b.shouldGetFrame(tuple.a))
                .map(timeTuple -> timeTuple.b);
    }

    public static Stream<Float> deltaOf(Stream<Integer> frameRateStream) {//TODO: Make this be called more like other primitives on streams.
        return stream(frameRateStream)
                .map(Time::getDeltaTime);
    }

    private float getDeltaTime() {
        return (float) deltaTime / SECOND;
    }
}