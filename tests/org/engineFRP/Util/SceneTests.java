package org.engineFRP.Util;

import org.engineFRP.core.FRPDisplay;
import org.engineFRP.core.Scene;
import org.engineFRP.core.Transform;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class SceneTests {

    @Before
    public void createDisplay() {
        FRPDisplay.createForTesting();
    }

    @Test
    public void testAddNode() {
        Node<Transform> searchNode = Node.newNode(new Transform(), "c");
        Scene.graph.add(
                Node.newNode(new Transform(), "a")
                        .addChild(Node.newNode(new Transform(), "b")
                                .addChild(searchNode))
                        .addChild(Node.newNode(new Transform(), "d")))
                .addChild(Node.newNode(new Transform(), "e"));

//        System.out.println(Scene.graph.toString());
        assertEquals(searchNode.nodeName, Scene.graph.find("c").nodeName);
        assertEquals(null, Scene.graph.find("x"));
    }
}