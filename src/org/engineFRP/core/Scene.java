package org.engineFRP.core;

import org.engineFRP.Util.Node;
import org.engineFRP.Util.Tree;
import org.engineFRP.rendering.shaders.Shader;
import org.engineFRP.rendering.shaders.SquareShader;

import java.util.Objects;

/**
 * Created by TekMaTek on 22/03/2015.
 */
public class Scene extends Tree<Transform> {

    public static final Scene graph = new Scene();
    private static Shader shader2;

    private Scene() {
        super(new Node<>(new Transform(), "root"));
        shader2 = new SquareShader();
    }

    public void drawScene() {
        drawScene(this.rootNode);
    }

    /**
     * Depth first rendering. Recursively called for each Transform and their children.
     *
     * @param trans the transform to recursively draw from.
     */
    private void drawScene(Node<Transform> trans) {
        for(Node<Transform> transform : trans.getChildren()) {
            shader2.draw(transform.value);
            drawScene(transform);
        }
    }

    @Override
    public Scene add(final Node<Transform> node) {
        rootNode.addChild(node);
        return this;
    }

    @Override
    public Scene add(final Transform node) {
        rootNode.addChild(node);
        return this;
    }

    @Override
    public Node<Transform> find(final String nodeName) {//TODO: Use Optionals here.
        return search(this.rootNode, nodeName);
    }

    private Node<Transform> search(final Node<Transform> node, final String search) {
        if(Objects.equals(node.nodeName, search)) {
            return node;
        } else {
            for(Node<Transform> child : node.getChildren()) {
                Node<Transform> _child = search(child, search);
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

    private String allNodes(final Node<Transform> node, String print) {
        print += node.nodeName + " depth" + node.getDepth() + " ";
        for(Node<Transform> child : node.getChildren()) {
            print = allNodes(child, print);
        }
        return print;
    }
}