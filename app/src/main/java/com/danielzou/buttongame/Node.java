package com.danielzou.buttongame;

/**
 * Represents a node in the button graph. Nodes will be paired with floating action buttons in a
 * magic button.
 */

public class Node {

    private int id;

    public Node(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node that = (Node) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
