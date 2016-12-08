package com.danielzou.buttongame;

/**
 * Represents an association or Edge between two magic buttons.
 */

public class Edge {

    private Node b1;
    private Node b2;

    public Edge(Node b1, Node b2) {
        this.b1 = b1;
        this.b2 = b2;
    }

    /**
     * Returns whether this association contains the inputted node.
     * @param node Node in question.
     * @return Whether this association contains the node.
     */
    public boolean hasNode(Node node) {
        return node.equals(b1) || node.equals (b2);
    }

}
