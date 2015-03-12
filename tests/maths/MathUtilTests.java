package maths;

import org.engineFRP.maths.MathUtil;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by TekMaTek on 11/03/2015.
 */
public class MathUtilTests {

    @Test
    public void testRangeConvert() {
        final float LOW = -100.0f, HIGH = 200.0f, NEW_LOW = 0.0f, NEW_HIGH = 100.0f;
        float[] testValues = new float[] {
                14,
                -64,
                125,
                HIGH,
                LOW
        };
        float newValue;
        newValue = MathUtil.RangeConvert(LOW, HIGH, NEW_LOW, NEW_HIGH, testValues[0]);
        assertEquals(38.0f, newValue);

        newValue = MathUtil.RangeConvert(LOW, HIGH, NEW_LOW, NEW_HIGH, testValues[1]);
        assertEquals(12.0f, newValue);

        newValue = MathUtil.RangeConvert(LOW, HIGH, NEW_LOW, NEW_HIGH, testValues[2]);
        assertEquals(75.0f, newValue);

        newValue = MathUtil.RangeConvert(LOW, HIGH, NEW_LOW, NEW_HIGH, testValues[3]);
        assertEquals(NEW_HIGH, newValue);

        newValue = MathUtil.RangeConvert(LOW, HIGH, NEW_LOW, NEW_HIGH, testValues[4]);
        assertEquals(NEW_LOW, newValue);
    }

    @Test
    public void testInvalidRange() {
        final float LOW = -100.0f, HIGH = 200.0f, NEW_LOW = 0.0f, NEW_HIGH = 100.0f;
        float unchanged = MathUtil.RangeConvert(LOW, HIGH, NEW_LOW, NEW_HIGH, 1000.0f);
        assertEquals(1000.0f, unchanged);
    }
}
