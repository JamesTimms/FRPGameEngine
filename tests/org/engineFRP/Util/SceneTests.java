package org.engineFRP.Util;

import org.engineFRP.FRP.FRPDisplay;
import org.engineFRP.core.GameObject;
import org.engineFRP.core.Scene;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class SceneTests {

    @Before
    public void createDisplay() {
        FRPDisplay.createForTesting();
    }

    @Test
    public void testAddNode() {
        Node<GameObject> searchNode = Node.newNode(new GameObject(), "c");
        Scene.graph.add(
                Node.newNode(new GameObject(), "a")
                        .addChild(Node.newNode(new GameObject(), "b")
                                .addChild(searchNode))
                        .addChild(Node.newNode(new GameObject(), "d"))
                        .addChild(Node.newNode(new GameObject(), "e")));

//        System.out.println(Scene.graph.toString());
        assertEquals(searchNode.nodeName, Scene.graph.find("c").nodeName);
        assertEquals(null, Scene.graph.find("x"));
    }
}