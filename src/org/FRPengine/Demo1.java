package org.FRPengine;

import org.FRPengine.core.FRPDisplay;

/**
 * Created by TekMaTek on 17/02/2015.
 */
public class Demo1 {

    public static void main(String[] args) {
        FRPDisplay.CreateDisplay();

        System.out.println("Hello FRP Game Engine");

        FRPDisplay.DestroyDisplay();
    }

}