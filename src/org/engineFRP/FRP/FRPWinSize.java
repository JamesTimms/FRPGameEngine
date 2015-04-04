package org.engineFRP.FRP;

import sodium.Cell;
import sodium.StreamSink;

/**
 * Created by TekMaTek on 30/03/2015.
 */
public class FRPWinSize extends StreamSink<FRPWinSize.Resize> {

    public Cell<Integer> width() {
        return map(size -> size.width)
                .hold(FRPDisplay.DEFAULT_WIDTH);
    }

    public Cell<Integer> height() {
        return map(size -> size.height)
                .hold(FRPDisplay.DEFAULT_HEIGHT);
    }

    public static class Resize {

        public long window;
        public int width;
        public int height;

        public Resize(long window, int width, int height) {
            this.window = window;
            this.width = width;
            this.height = height;
        }
    }

}
