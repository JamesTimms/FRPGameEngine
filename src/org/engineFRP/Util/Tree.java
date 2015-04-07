package org.engineFRP.Util;

import sodium.Cell;

/**
 * Created by TekMaTek on 22/03/2015.
 */
public abstract class Tree<A> {

    protected Node<A> rootNode;

    public Tree(Node<A> rootNode) {
        this.rootNode = rootNode;
    }

    public Tree<A> add(final Node<A> node) {
        rootNode.addChild(node);
        return this;
    }

    public Tree<A> add(final Cell<A> node, String name) {
        rootNode.addChild(node, name);
        return this;
    }

    public abstract Node<A> find(final String nodeName);

}