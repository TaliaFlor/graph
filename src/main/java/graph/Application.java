package graph;

import graph.data.EdgeReader;
import graph.data.NodeReader;
import graph.model.Edge;
import graph.model.Node;
import graph.representation.Graph;

import java.util.Deque;
import java.util.List;

public class Application {

    private static final String PATH = "src/main/resources";
    private static final int NUM_LINES_TO_SKIP = 1;
    private static final char SEPARATOR = ',';


    public static void main(String[] args) {
//            Graph graph = readSimpleGraph();

        Graph graph = readMazeGraph();

        Deque<Node> path = graph.search(1, 10);

        System.out.println(path);
    }

    private static Graph readSimpleGraph() {
        List<Node> nodes = readNodesFile("/examples/simple/nodes.csv");
        List<Edge> edges = readEdgesFile("/examples/simple/edges.csv", nodes);

        // Create graph
        Graph graph = new Graph();
        graph.add(edges);

        return graph;
    }

    private static Graph readMazeGraph() {
        List<Node> nodes = readNodesFile("/examples/maze/nodes.csv");
        List<Edge> edges = readEdgesFile("/examples/maze/edges.csv", nodes);

        // Create graph
        Graph graph = new Graph();
        graph.add(edges);

        return graph;
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
