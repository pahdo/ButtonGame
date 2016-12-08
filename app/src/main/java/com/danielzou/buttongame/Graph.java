package com.danielzou.buttongame;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * This class represents a directed graph of nodes and edges, intended to abstract magic buttons and their
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
            mNodes.add(node);
        }
        mEdges = new HashSet<>();
        makeEdges(nodeDegree);
    }

    /**
     * Draws arrows between a set of n nodes, where each node has maxDegree edges.
     * Warning: if you attempt to generate a graph that cannot possibly be generated, this
     * method will recursively call itself infinitely many times.
     * @param maxDegree The desired degree of each node.
     */
    public void makeEdges(int maxDegree) {

        Set<Node> openSet = new HashSet<>();
        openSet.addAll(mNodes);
        Set<Edge> tempEdges = new HashSet<>();

        for (Node node : mNodes) {
            openSet.remove(node);
            while(getOutdegree(tempEdges, node) < maxDegree) {
                Node other = chooseRandomNodeFromSet(openSet);
                linkNodes(tempEdges, node, other);
            }
            openSet.add(node);
        }

        mEdges.addAll(tempEdges);

    }

    public int getOutdegree(Set<Edge> edges, Node node) {
        int numEdges = 0;
        for (Edge edge : edges) {
            if (edge.getNode1().equals(node)) {
                numEdges ++;
            }
        }
        return numEdges;
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
     * Helper function for makeEdges, which performs the work of adding an edge to
     * the edges set.
     * @param b1 Node 1.
     * @param b2 Node 2.
     */
    private void linkNodes(Set<Edge> edges, Node b1, Node b2) {
        Edge link = new Edge(b1, b2);
        edges.add(link);
    }

    public Set<Node> getNodes() {
        return mNodes;
    }

    public Set<Edge> getEdges() {
        return mEdges;
    }
}
