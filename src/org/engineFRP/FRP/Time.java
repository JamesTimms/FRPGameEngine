package org.engineFRP.FRP;

/**
 * Created by TekMaTek on 21/03/2014.
 */
public class Time {

    public static final int FRAME_CAP = 120,
            INSTANTLY = 0,
            ONE_PER_SECOND = 1,
            TEN_PER_SECOND = 10,
            THIRTY_PER_SECOND = 30;
    public static final long SECOND = 1000000000L;

    public static long getNanoTime() {
        return System.nanoTime();
    }

    public static long getTime() {
        return getNanoTime() / Time.SECOND;
    }

    public int frameRate;
    private Double timeElapsed = 0.0d;
    private Long timeDiff = 0l,
            lastDeltaTime,
            deltaTime = 0l;

    public Time(int frameRate) {
        this.frameRate = frameRate;
        lastDeltaTime = getNanoTime();
    }

    public boolean shouldGetFrame() {
        Long timeNow = getNanoTime();
        Long lastTime = timeNow - timeDiff;
        timeDiff = timeNow;
        long _frameRate = (frameRate > FRAME_CAP) ? FRAME_CAP : frameRate;
        long frameThrottle = (_frameRate <= 0) ? 0 : SECOND / _frameRate;
        timeElapsed = timeElapsed + lastTime;
        boolean isReady = timeElapsed > frameThrottle;
        if(isReady) {
            timeElapsed = 0.0d;
            deltaTime = timeNow - lastDeltaTime;
            lastDeltaTime = timeNow;
        }
        return isReady;
    }

    public float getDeltaTime() {
        return (float) deltaTime / SECOND;
    }
}