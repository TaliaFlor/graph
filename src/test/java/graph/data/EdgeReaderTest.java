package graph.data;

import graph.model.Edge;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EdgeReaderTest {

    private static final String FILENAME = "src/main/resources/edges.csv";
    private static final int NUM_LINES_TO_SKIP = 1;
    private static final char SEPARATOR = ',';


    EdgeReader reader;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        reader = null;
    }


    @Test
    void testEdgeFileReader() {
        reader = new EdgeReader(new ArrayList<>(), FILENAME, NUM_LINES_TO_SKIP, SEPARATOR);
        List<Edge> nodeList = reader.read();
        assertNotNull(nodeList);
    }

}
