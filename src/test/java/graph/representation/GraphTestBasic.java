package graph.representation;

import graph.model.Edge;
import graph.model.Node;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class GraphTestBasic {   //TODO update tests

    Graph graph;


    @BeforeEach
    void setUp() {
        graph = new Graph();
    }

    @AfterEach
    void tearDown() {
        graph = null;
    }


    @Test
    void testAddDirectedEdge() {
        // given
        int originIndex = 1;
        int destinyIndex = 2;

        Edge edge = new Edge(
                1,
                new Node(originIndex),
                new Node(destinyIndex),
                true
        );

        // when
        graph.add(edge);

        //then
//        assertNotNull(graph.get(originIndex));
//        assertNull(graph.get(destinyIndex));
    }

    @Test
    void testAddUndirectedEdge() {
        // given
        int originIndex = 1;
        int destinyIndex = 2;

        Edge edge = new Edge(
                1,
                new Node(originIndex),
                new Node(destinyIndex),
                false
        );

        // when
        graph.add(edge);

        //then
//        assertNotNull(graph.get(originIndex));
//        assertNotNull(graph.get(destinyIndex));
    }

    @Test
    @Disabled
    void testGetGraph() {
        //TODO json ToString
        int originIndex = 1;
        int destinyIndex = 2;

        Edge edge = new Edge(
                1,
                new Node(originIndex),
                new Node(destinyIndex),
                false
        );

        graph.add(edge);

        System.out.println(graph);
    }


    @Test
    @Disabled
    void testGetNode() {
        System.out.println(new Node(1));
    }


    @Test
    @Disabled
    void testGetEdge() {
        int originIndex = 1;
        int destinyIndex = 2;

        Edge edge = new Edge(
                1,
                new Node(originIndex),
                new Node(destinyIndex),
                false
        );

        System.out.println(edge);
    }

}