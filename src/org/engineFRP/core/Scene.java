package org.engineFRP.core;

import org.engineFRP.Util.Node;
import org.engineFRP.Util.Tree;
import sodium.Cell;

import java.util.Objects;

/**
 * Created by TekMaTek on 22/03/2015.
 */
public class Scene extends Tree<GameObject> {

    public static final Scene graph = new Scene();

    private Scene() {
        super(new Node<>(new GameObject(), "root"));
    }

    public void drawScene() {
        drawScene(this.rootNode);
    }

    /**
     * Depth first rendering. Recursively called for each Transform and their children.
     *
     * @param trans the transform to recursively draw from.
     */
    private void drawScene(Node<GameObject> trans) {
        for(Node<GameObject> transform : trans.getChildren()) {
            transform.sample().mesh.shader.draw(transform.sample());
            drawScene(transform);
        }
    }

    @Override
    public Scene add(final Node<GameObject> node) {
        rootNode.addChild(node);
        return this;
    }

    public Scene add(final GameObject node) {
        rootNode.addChild(new Cell<>(node), node.name);
        return this;
    }

    @Override
    public Scene add(final Cell<GameObject> node, String name) {
        rootNode.addChild(node, name);
        return this;
    }

    @Override
    public Node<GameObject> find(final String nodeName) {//TODO: Use Optionals here.
        return search(this.rootNode, nodeName);
    }

    @Override
    public Scene destroy(final GameObject node) {
        Node<GameObject> go = search(this.rootNode, node);
        if(go != null) {
            go.Delete();
        }
        return this;
    }

    private Node<GameObject> search(final Node<GameObject> node, final String search) {
        if(Objects.equals(node.nodeName, search)) {
            return node;
        } else {
            for(Node<GameObject> child : node.getChildren()) {
                Node<GameObject> _child = search(child, search);
                if(_child != null) {
                    return _child;
                }
            }
        }
        return null;
    }

    private Node<GameObject> search(final Node<GameObject> node, final GameObject search) {
        if(node.sample().equals(search)) {
            return node;
        } else {
            for(Node<GameObject> child : node.getChildren()) {
                Node<GameObject> _child = search(child, search);
                if(_child != null) {
                    return _child;
                }
            }
        }
        return null;
    }

    public String toString() {
        return allNodes(this.rootNode, "");
    }

    private String allNodes(final Node<GameObject> node, String print) {
        print += node.nodeName + " depth" + node.getDepth() + " ";
        for(Node<GameObject> child : node.getChildren()) {
            print = allNodes(child, print);
        }
        return print;
    }
}