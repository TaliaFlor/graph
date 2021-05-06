package graph.representation;

import graph.data.EdgeReader;
import graph.data.NodeReader;
import graph.dijkstra.DijkstraModel;
import graph.model.Edge;
import graph.model.Node;
import org.junit.jupiter.api.*;

import java.util.Deque;
import java.util.List;
import java.util.Map;

class GraphTestDijkstra {
    private static final String PATH = "src/main/resources/examples/shortest path/";
    private static final String NODES_FILENAME = PATH + "nodes.csv";
    private static final String EDGES_FILENAME = PATH + "edges.csv";

    static List<Edge> edges;

    Graph graph;


    @BeforeAll
    static void init() {
        NodeReader nodeReader = new NodeReader(NODES_FILENAME);
        List<Node> nodes = nodeReader.read();
        EdgeReader edgeReader = new EdgeReader(nodes, EDGES_FILENAME);
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
    void testDijkstra() {
        List<DijkstraModel> shortestPaths = graph.shortestPath(1);
        System.out.println(shortestPaths);
    }
}