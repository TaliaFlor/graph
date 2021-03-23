package graph.data;

import graph.model.Node;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NodeReaderTest {

    private static final String FILENAME = "src/main/resources/nodes.csv";
    private static final int NUM_LINES_TO_SKIP = 1;
    private static final char SEPARATOR = ',';


    NodeReader reader;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        reader = null;
    }


    @Test
    void testNodeFileReader() {
        reader = new NodeReader(FILENAME, NUM_LINES_TO_SKIP, SEPARATOR);
        List<Node> nodeList = reader.read();
        assertNotNull(nodeList);
    }

}
