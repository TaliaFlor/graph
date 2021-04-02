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
            List<Node> nodeList = readNodesFile("/nodes.csv");
            List<Edge> edgeList = readEdgesFile("/edges.csv", nodeList);

            // Creating graphs
            Graph graph = new Graph();
            graph.add(edgeList);

            System.out.println(graph);
        } catch (Exception e) {
            log.error("Error during graph creation");
            e.printStackTrace();
        }
    }

    private static List<Node> readNodesFile(String filename) {
        NodeReader nodeReader = new NodeReader(PATH + filename, NUM_LINES_TO_SKIP, SEPARATOR);
        return nodeReader.read();
    }

    private static List<Edge> readEdgesFile(String filename, List<Node> nodeList) {
        EdgeReader edgeReader = new EdgeReader(nodeList, PATH + filename, NUM_LINES_TO_SKIP, SEPARATOR);
        return edgeReader.read();
    }

}
