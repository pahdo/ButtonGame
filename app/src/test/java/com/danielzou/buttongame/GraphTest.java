package com.danielzou.buttongame;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test suite for the graph, edge, and node classes.
 */
public class GraphTest {

    Graph graph4_1;
    Graph graph5_2;
    Graph graph6_3;

    @Before
    public void setUp() throws Exception {
        graph4_1 = new Graph(4, 1);
        graph5_2 = new Graph(5, 2);
        graph6_3 = new Graph(6, 3);
    }

    @Test
    public void expectedEdges() throws Exception {
        assertEquals(4, (long) graph4_1.getEdges().size());
        assertEquals(10, (long) graph5_2.getEdges().size());
        assertEquals(18, (long) graph6_3.getEdges().size());
    }

}