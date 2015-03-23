package org.engineFRP.Util;

/**
 * Created by TekMaTek on 22/03/2015.
 */
public abstract class Tree<A> {

    protected Node<A> rootNode;

    public Tree(Node<A> rootNode) {
        this.rootNode = rootNode;
    }

    public Node<A> add(final Node<A> node) {
        return rootNode.addChild(node);
    }

    public abstract Node<A> find(final String nodeName);

}