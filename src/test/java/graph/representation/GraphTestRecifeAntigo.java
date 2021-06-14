package graph.representation;

import graph.data.EdgeReader;
import graph.data.NodeReader;
import graph.dijkstra.DijkstraSimpleModel;
import graph.model.Edge;
import graph.model.Node;
import org.junit.jupiter.api.*;
import util.Writer;

import java.util.Deque;
import java.util.List;

class GraphTestRecifeAntigo {
    private static final String PATH = "src/main/resources/examples/recife antigo/";
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
    void testDijkstraWithInitialNode() {
        List<DijkstraSimpleModel> shortestPaths = graph.shortestPath(1);
        System.out.println(shortestPaths);
    }

    @Test
    void testDijkstraWithInitialNodeAndFinalNode() {
        Deque<DijkstraSimpleModel> shortestPaths = graph.shortestPath(1, 49);
        Writer.write(shortestPaths.toString(), "src/test/java/util/recife antigo.json");
    }

}