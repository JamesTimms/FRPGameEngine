package org.FRPengine.core;

/**
 * Created by TekMaTek on 21/03/2014.
 */
public class Time {

    public static final long FRAME_CAP = 60;
    public static final long ONE_PER_SECOND = 1;
    public static final long TEN_PER_SECOND = 10;
    public static final long THIRTY_PER_SECOND = 30;

    public static final long SECOND = 1000000000L;
    public static long perLoopDelta;
    protected static double timeElapsed = 0.0d;
    static long timeLastLoop;
    static long timeThisLoop;

    public static long getTime() {
        return System.nanoTime();
    }

    public static boolean readyForFrameRate(long frame_rate) {
        timeThisLoop = getTime();
        perLoopDelta = timeThisLoop - timeLastLoop;
        timeLastLoop = timeThisLoop;
        long _frame_rate = (frame_rate > FRAME_CAP) ? FRAME_CAP : frame_rate;
        long frameThrottle = (_frame_rate == 0) ? 0 : SECOND / _frame_rate;
        boolean isReady = (timeElapsed += perLoopDelta) > frameThrottle;
        if(isReady) {
            timeElapsed = 0;
        }
        return isReady;
    }
}
