package graph.representation;

import graph.data.EdgeReader;
import graph.data.NodeReader;
import graph.model.Edge;
import graph.model.Node;
import org.junit.jupiter.api.*;

import java.util.Deque;
import java.util.List;
import java.util.Map;

class GraphTestBfsSearch {

    static List<Edge> edges;

    Graph graph;


    @BeforeAll
    static void init() {
        NodeReader nodeReader = new NodeReader("src/main/resources/examples/maze/nodes.csv");
        List<Node> nodes = nodeReader.read();
        EdgeReader edgeReader = new EdgeReader(nodes, "src/main/resources/examples/maze/edges.csv");
        edges = edgeReader.read();
    }

    @AfterAll
    static void close() {
        edges = null;
    }

    @BeforeEach
    void setUp() {
        graph = new Graph();
        graph.add(edges);
    }

    @AfterEach
    void tearDown() {
        graph = null;
    }


    @Test
    void testSearchGraph() {
        Deque<Node> path = graph.search(10);
        System.out.println(path);
    }

    @Test
    void testSearchGraphWithInitialNode() {
        Deque<Node> path = graph.search(1, 10);
        System.out.println(path);
    }

    @Test
    void testWalkGraph() {
        Map<Node, Node> predecessors = graph.walk();
        System.out.println(predecessors);
    }


    @Test
    void testWalkGraphWithInitialNode() {
        Map<Node, Node> predecessors = graph.walk(4);
        System.out.println(predecessors);
    }

}