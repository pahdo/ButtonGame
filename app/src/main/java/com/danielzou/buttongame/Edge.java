package com.danielzou.buttongame;

/**
 * Represents an association or Edge between two magic buttons.
 */

public class Edge {

    private Node n1;
    private Node n2;

    public Edge(Node n1, Node n2) {
        this.n1 = n1;
        this.n2 = n2;
    }

    public Node getNode1() {
        return n1;
    }

    public Node getNode2() {
        return n2;
    }

}
