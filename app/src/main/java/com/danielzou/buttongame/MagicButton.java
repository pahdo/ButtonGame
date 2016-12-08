package com.danielzou.buttongame;

import android.support.design.widget.FloatingActionButton;

/**
 * Magic buttons are pairings of floating action buttons and nodes so that the button can be linked
 * to magic buttons in a graph.
 */

public class MagicButton {

    private FloatingActionButton button;
    private Node node;

    public MagicButton(FloatingActionButton button, Node node) {
        this.button = button;
        this.node = node;
    }

    public FloatingActionButton getButton() {
        return button;
    }

    public Node getNode() {
        return node;
    }
}
