package com.danielzou.buttongame;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * This class represents a graph of nodes and edges, intended to abstract magic buttons and their
 * links or associations.
 */

public class Graph {

    private final Random random = new Random();
    private Set<Node> mNodes;
    private Set<Edge> mEdges;

    public Graph(int numNodes, int nodeDegree) {
        mNodes = new HashSet<>();
        for (int i = 0; i < numNodes; i++) {
            Node node = new Node(i);
        }
        makeEdges(nodeDegree);
    }

    /**
     * Draws edges between a set of n node, where each node has maxDegree edges.
     * @param maxDegree The desired degree of each node.
     */
    public void makeEdges(int maxDegree) {

        Set<Node> openSet = mNodes;
        Set<Edge> tempEdges = new HashSet<>();

        for (Node node : openSet) {
            openSet.remove(node);
            while(getDegree(tempEdges, node) < maxDegree) {
                Node other = chooseRandomNodeFromSet(openSet);
                if (getDegree(tempEdges, other) >= maxDegree) {
                    openSet.remove(other);
                } else if (openSet.size() == 1 && sharesEdgeWith(tempEdges, node, other)) {
                    // The graph generated so far means a button has to be linked with another
                    // button twice, so we will try generating the graph again from the beginning.
                    makeEdges(maxDegree);
                    return;
                } else {
                    linkNodes(tempEdges, node, other);
                }
            }
        }

    }

    public int getDegree(Set<Edge> associations, Node node) {
        int edges = 0;
        for (Edge edge : associations) {
            if (edge.hasNode(node)) {
                edges ++;
            }
        }
        return edges;
    }

    /**
     * A helper function for makeEdges.
     * @param set Set to choose an element from.
     * @return Randomly chosen element.
     */
    private Node chooseRandomNodeFromSet(Set<Node> set) {
        int size = set.size();
        int item = random.nextInt(size);
        int i = 0;
        for(Node node : set)
        {
            if (i == item)
                return node;
            i = i + 1;
        }
        return null;
    }

    /**
     * A helper function for makeEdges which returns whether there exists an Edge between
     * the two passed-in buttons.
     * @param b1 Node 1.
     * @param b2 Node 2.
     * @return Whether there is an Edge between button 1 and button 2.
     */
    private boolean sharesEdgeWith(Set<Edge> edges, Node b1, Node b2) {
        for (Edge edge : edges) {
            if (edge.hasNode(b1) && edge.hasNode(b2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Helper function for makeEdges, which performs the work of adding an edge to
     * the edges set.
     * @param b1 Node 1.
     * @param b2 Node 2.
     */
    private void linkNodes(Set<Edge> edges, Node b1, Node b2) {
        Edge link = new Edge(b1, b2);
        edges.add(link);
    }

}
