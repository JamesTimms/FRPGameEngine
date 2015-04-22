package org.engineFRP.FRP;

import sodium.Lambda1;
import sodium.Stream;
import sodium.StreamSink;

import java.util.ArrayList;

/**
 * Created by TekMaTek on 21/03/2014.
 */
public class FRPTime extends Time {

    private static final Lambda1<FRPTime, Float> toDelta = Time::getDeltaTime;
    private static ArrayList<FRPTime> allTime = new ArrayList<>();
    private StreamSink<FRPTime> timeStream = new StreamSink<>();

    private FRPTime(int frameRate) {
        super(frameRate);
        allTime.add(this);
    }

    public static long getTime() {
        return System.nanoTime();
    }

    public static void pollStreams() {
        for(FRPTime time : allTime) {
            time.timeStream.send(time);//Sort of a pulse for the time stream.
        }
    }

    public static Stream<Float> streamDelta(int frameRate) {
        return stream(frameRate)
                .map(toDelta);
    }

    public static Stream<FRPTime> stream(int frameRate) {
        return new FRPTime(frameRate).timeStream
                .filter(Time::shouldGetFrame);
    }
}