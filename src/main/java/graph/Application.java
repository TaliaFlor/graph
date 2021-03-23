package graph;

import graph.data.EdgeReader;
import graph.data.NodeReader;
import graph.model.Edge;
import graph.model.Node;
import graph.representation.Graph;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Application {

    private static final String PATH = "src/main/resources";
    private static final int NUM_LINES_TO_SKIP = 1;
    private static final char SEPARATOR = ',';


    public static void main(String[] args) {
        try {
            // Reading nodes
            NodeReader nodeReader = new NodeReader(PATH + "/nodes.csv", NUM_LINES_TO_SKIP, SEPARATOR);
            List<Node> nodeList = nodeReader.read();

            // Reading edges
            EdgeReader edgeReader = new EdgeReader(nodeList, PATH + "/edges.csv", NUM_LINES_TO_SKIP, SEPARATOR);
            List<Edge> edgeList = edgeReader.read();

            // Creating graphs
            Graph graph = new Graph();
            graph.add(edgeList);

            // Showing results
            System.out.println(graph);
        } catch (Exception e) {
            log.error("Error during graph creation");
            e.printStackTrace();
        }
    }

}
