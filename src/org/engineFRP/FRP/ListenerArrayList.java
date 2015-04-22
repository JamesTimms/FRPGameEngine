package org.engineFRP.FRP;

import sodium.Listener;

import java.util.ArrayList;

/**
 * Created by TekMaTek on 22/04/2015.
 */
public class ListenerArrayList extends ArrayList<Listener> {
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        this.forEach(sodium.Listener::unlisten);
    }
}
