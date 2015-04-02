package org.engineFRP.Util;

import org.junit.Test;

import java.util.Objects;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class NodeTests {

    @Test
    public void testNewNode() throws Exception {
        Node<Integer> myNode = Node.newNode(0);
        assertTrue(myNode.getClass() == Node.class);
        assertTrue(Objects.equals(myNode.nodeName, Node.defaultNodeName));
        assertEquals(0, myNode.value.intValue());
    }

    @Test
    public void testLift() throws Exception {
        Node<Integer> myNode = Node.newNode(0);
        assertTrue(myNode.getClass() == Node.class);
        assertTrue(Objects.equals(myNode.nodeName, Node.defaultNodeName));
        assertEquals(0, myNode.value.intValue());
    }

    @Test
    public void testNewNode1() throws Exception {
        Node<Integer> myNode = Node.newNode(0, "myAmazingNode");
        assertTrue(myNode.getClass() == Node.class);
        assertTrue(myNode.isRoot());
        assertTrue(myNode.isLeaf());
        assertTrue(Objects.equals(myNode.nodeName, "myAmazingNode"));
        assertEquals(0, myNode.value.intValue());
    }

    @Test
    public void testAddChildren() throws Exception {
        Node<Integer> myNode =
                Node.newNode(0)
                        .addChildren(Node.newNode(2), Node.newNode(4), Node.newNode(5), Node.newNode(66));
        assertEquals(0, myNode.value.intValue());
        assertEquals(4, myNode.getChildren().size());
        assertEquals(2, myNode.getChildren().get(0).value.intValue());
    }

    @Test
    public void testAddChild() throws Exception {
        Node<Integer> myNode =
                Node.newNode(0)
                        .addChild(Node.newNode(2))
                        .addChild(Node.newNode(3)
                                .addChild(Node.newNode(6)))
                        .addChild(Node.newNode(-2));
        assertEquals(0, myNode.value.intValue());
        assertEquals(3, myNode.getChildren().size());
        assertEquals(6, myNode.getChildren().get(1).getChildren().get(0).value.intValue());
    }

    @Test
    public void testAddChildren1() throws Exception {
        Node<Integer> myNode =
                Node.newNode(0)
                        .addChildren(2, 4, 5, 66);
        assertEquals(0, myNode.value.intValue());
        assertEquals(4, myNode.getChildren().size());
        assertEquals(2, myNode.getChildren().get(0).value.intValue());
    }

    @Test
    public void testAddChild1() throws Exception {
        Node<Integer> myNode =
                Node.newNode(0)
                        .addChild(2)
                        .addChild(Node.newNode(3)
                                .addChild(6))
                        .addChild(-2);
        assertEquals(0, myNode.value.intValue());
        assertEquals(3, myNode.getChildren().size());
        assertEquals(6, myNode.getChildren().get(1).getChildren().get(0).value.intValue());
    }

    @Test
    public void testGetSiblings() throws Exception {
        Node<Integer> myNode =
                Node.newNode(0)
                        .addChildren(2, 4, 5, 66);
        assertEquals(0, myNode.value.intValue());
        assertEquals(3, myNode.getChildren().get(0).getSiblings().size());
        assertEquals(4, myNode.getChildren().size());
    }

    @Test
    public void testGetChildren() throws Exception {
        Node<Integer> myNode =
                Node.newNode(0)
                        .addChildren(2, 4, 5, 66);
        assertEquals(0, myNode.value.intValue());
        assertEquals(4, myNode.getChildren().size());
    }

    @Test
    public void testGetParent() throws Exception {
        Node<Integer> myNode =
                Node.newNode(0)
                        .addChildren(2, 4, 5, 66);
        assertEquals(myNode, myNode.getChildren().get(0).getParent());
    }

    @Test
    public void testIsLeaf() throws Exception {
        Node<Integer> myNode = Node.newNode(0);
        assertTrue(myNode.isRoot());
        assertTrue(myNode.isLeaf());
        assertEquals(0, myNode.value.intValue());
    }

    @Test
    public void testIsRoot() throws Exception {
        Node<Integer> myNode = Node.newNode(0);
        assertTrue(myNode.isRoot());
        assertTrue(myNode.isLeaf());
        assertEquals(0, myNode.value.intValue());
    }

    @Test
    public void testGetDepth() throws Exception {
        Node<Integer> myNode =
                Node.newNode(0)
                        .addChild(2)
                        .addChild(Node.newNode(3)
                                .addChild(6))
                        .addChild(-2);
        assertEquals(0, myNode.getDepth());
        assertEquals(1, myNode.getChildren().get(0).getDepth());
        assertEquals(2, myNode.getChildren().get(1).getChildren().get(0).getDepth());
    }
}