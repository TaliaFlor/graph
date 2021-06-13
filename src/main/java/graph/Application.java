package graph;

import graph.data.EdgeReader;
import graph.data.NodeReader;
import graph.model.Edge;
import graph.model.Node;
import graph.representation.Graph;

import java.util.List;

public class Application {

    private static final String PATH = "src/main/resources";


    public static void main(String[] args) {
//        Graph graph = readGraph("simple/nodes.csv", "simple/edges.csv");
//        Graph graph = readGraph("maze/nodes.csv", "maze/edges.csv");
        Graph graph = readGraph("shortest path/nodes.csv", "shortest path/edges.csv");


        System.out.println(graph);
    }

    private static Graph readGraph(String nodesFile, String edgesFile) {
        List<Node> nodes = readNodesFile("/examples/" + nodesFile);
        System.out.println(nodes);
        List<Edge> edges = readEdgesFile("/examples/" + edgesFile, nodes);
        System.out.println(edges);

        // Create graph
        Graph graph = new Graph();
        graph.add(edges);

        return graph;
    }

    private static List<Node> readNodesFile(String filename) {
        NodeReader nodeReader = new NodeReader(PATH + filename);
        return nodeReader.read();
    }

    private static List<Edge> readEdgesFile(String filename, List<Node> nodeList) {
        EdgeReader edgeReader = new EdgeReader(nodeList, PATH + filename);
        return edgeReader.read();
    }

}
