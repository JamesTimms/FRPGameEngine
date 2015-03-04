package org.FRPengine.core;

/**
 * Created by TekMaTek on 21/03/2014.
 */
public class Time {

    public static final long FRAME_CAP = 120;
    public static final long ONE_PER_SECOND = 1;
    public static final long TEN_PER_SECOND = 10;
    public static final long THIRTY_PER_SECOND = 30;
    public static final long SECOND = 1000000000L;
    
    long perLoopDelta;
    double timeElapsed = 0.0d;
    long timeLastLoop;
    long timeThisLoop;
    
    long previousDeltaTime;
    long deltaTime;

    public static long getTime() {
        return System.nanoTime();
    }

    public boolean shouldGetFrame(long frame_rate) {
        timeThisLoop = getTime();
        perLoopDelta = timeThisLoop - timeLastLoop;
        timeLastLoop = timeThisLoop;
        long _frame_rate = (frame_rate > FRAME_CAP) ? FRAME_CAP : frame_rate;
        long frameThrottle = (_frame_rate == 0) ? 0 : SECOND / _frame_rate;
        boolean isReady = (timeElapsed += perLoopDelta) > frameThrottle && (_frame_rate > 0);
        if(isReady) {
            timeElapsed = 0;
            deltaTime = previousDeltaTime - deltaTime;
            previousDeltaTime = timeThisLoop;
        }
        return isReady;
    }

    public long getDeltaTime() {
        return deltaTime;
    }
}