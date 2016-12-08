package com.danielzou.buttongame;

import android.support.v7.widget.SwitchCompat;

import java.util.HashSet;
import java.util.Set;

/**
 * Magic buttons are pairings of switch compats and nodes so that the switchCompat can be linked
 * to magic buttons in a graph.
 */

public class MagicButton {

    private SwitchCompat switchCompat;
    private Node node;
    // Same id as node.
    private int id;
    // Set of switch compats corresponding to the magic buttons whose nodes this magic switchCompat's
    // node shares an edge with.
    private Set<SwitchCompat> switchCompatLinks;

    public MagicButton(SwitchCompat switchCompat, Node node) {
        this.switchCompat = switchCompat;
        this.node = node;
        this.id = node.getId();
        this. switchCompatLinks = new HashSet<>();
    }

    public SwitchCompat getSwitchCompat() {
        return switchCompat;
    }

    public Node getNode() {
        return node;
    }

    public int getId() {
        return id;
    }

    public Set<SwitchCompat> getSwitchCompatLinks() {
        return switchCompatLinks;
    }

    public void appendToSwitchCompatLinks(SwitchCompat sc) {
        this.switchCompatLinks.add(sc);
    }
}
