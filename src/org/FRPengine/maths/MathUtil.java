package org.FRPengine.maths;

/**
 * Created by TekMaTek on 11/03/2015.
 */
public class MathUtil {

    public static float RangeConvert(float start, float end, float newStart, float newEnd, float value) {
        if(value < start || value > end) {
            System.err.println("Value was outside initial range.");
            return value;
        }
        double scale = (double) (newEnd - newStart) / (end - start);
        return (float) (newStart + ((value - start) * scale));
    }
}