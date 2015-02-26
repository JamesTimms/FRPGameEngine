package org.FRPengine;

import org.FRPengine.core.FRPDisplay;
import org.FRPengine.rendering.SimpleRenderer;

/**
 * Created by TekMaTek on 26/02/2015.
 */
public class Demo2 {

    public static void main(String[] args) {
        new Demo2();
    }

    public Demo2() {
        FRPDisplay.Create();
        SimpleRenderer.loop();
    }

}
