package org.engineFRP.Util;

import org.lwjgl.Sys;
import sodium.Cell;

import java.util.ArrayList;

/**
 * Created by TekMaTek on 22/03/2015.
 */
public class Node<A> {

    public static final String defaultNodeName = "newNode";
    public Cell<A> value;
    public String nodeName;
    private Node<A> parent;
    private ArrayList<Node<A>> children = new ArrayList<>();

    public Node(A a) {
        this(a, defaultNodeName);
    }

    public Node(A a, String nodeName) {
        this(new Cell<>(a), nodeName);
    }

    public Node(Cell<A> a, String nodeName) {
        value = a;
        this.nodeName = nodeName;
    }

    public static <B> Node<B> newNode(B b) {
        return new Node<>(b);
    }

    public static <B> Node<B> newNode(B b, String nodeName) {
        Node<B> newNode = newNode(b);
        newNode.nodeName = nodeName;
        return newNode;
    }

    /**
     * These methods shouldn't cause heap pollution but are warned that they might.
     *
     * @param children
     * @return
     */
    @SafeVarargs
    public final Node<A> addChildren(Node<A>... children) {
        for(Node<A> child : children) {
            this.addChild(child);
        }
        return this;
    }

    public Node<A> addChild(Node<A> child) {//TODO: Make this use optionals.
        child.parent = this;
        children.add(child);
        return this;
    }

    public Node<A> addChild(Cell<A> child, String name) {//TODO: Make this use optionals.
        return addChild(new Node<>(child, name));
    }

    /**
     * These methods shouldn't cause heap pollution but are warned that they might.
     *
     * @param children
     * @return
     */
    @SafeVarargs
    public final Node<A> addChildren(A... children) {
        for(A child : children) {
            this.addChild(new Node<>(child));
        }
        return this;
    }

    public Node<A> addChild(A child) {//TODO: Make this use optionals.
        return addChild(new Node<>(child, defaultNodeName));
    }

    public ArrayList<Node<A>> getSiblings() {
        ArrayList<Node<A>> sib = (ArrayList<Node<A>>) getParent().getChildren().clone();
        sib.remove(this);
        return sib;
    }

    public A sample() {
        return value.sample();
    }

    public ArrayList<Node<A>> getChildren() {
        return children;
    }

    public Node<A> getParent() {
        return parent;
    }

    public boolean isLeaf() {
        return this.getChildren().size() == 0;
    }

    public boolean isRoot() {
        return this.parent == null;
    }

    public int getDepth() {//TODO: can this be calculated only when it changes?
        int depth = 0;
        Node<A> p = parent;
        while(p != null) {
            depth++;
            p = p.parent;
        }
        return depth;
    }

    public void Delete() {
        this.value = null;
        for(Node<A> node : this.children) {
            node.parent = this.parent;
            this.parent.children.add(node);
        }
        this.children = null;
        this.parent.children.remove(this);
        this.parent = null;
//        System.gc();
    }
}