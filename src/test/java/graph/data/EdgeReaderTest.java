package graph.data;

import graph.model.Edge;
import graph.model.Node;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EdgeReaderTest {

    private static final String PATH = "src/main/resources/examples/shortest path/";
    private static final String NODES_FILENAME = PATH + "nodes.csv";
    private static final String EDGES_FILENAME = PATH + "edges.csv";

    NodeReader nodeReader;
    EdgeReader edgeReader;

    List<Node> nodes;

    @BeforeEach
    void setUp() {
        nodeReader = new NodeReader(NODES_FILENAME);
        nodes = nodeReader.read();
    }

    @AfterEach
    void tearDown() {
        nodes = null;

        nodeReader = null;
        edgeReader = null;
    }


    @Test
    void testEdgeFileReader() {
        edgeReader = new EdgeReader(nodes, EDGES_FILENAME);
        List<Edge> edges = edgeReader.read();
        assertNotNull(edges);
        System.out.println(edges);
    }

}
