package org.engineFRP.Util;

import java.util.ArrayList;

/**
 * Created by TekMaTek on 30/03/2015.
 */
public class Util {

    public static int[] toIntArray(ArrayList<Integer> array) {
        Integer[] temp = new Integer[array.size()];
        int[] out = new int[array.size()];
        temp = array.toArray(temp);
        for(int i = 0; i < array.size(); i++) {
            out[i] = temp[i];
        }
        return out;
    }
}
