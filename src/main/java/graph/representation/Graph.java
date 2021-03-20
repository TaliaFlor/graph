package graph.representation;

import graph.model.Edge;
import graph.model.Node;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Graph {

    @Getter
    private final Map<Integer, List<Node>> graph = new HashMap<>();


    public List<Node> get(int index) {
        return graph.get(index);
    }

    public void add(List<Edge> edges) {
        for (Edge edge : edges) {
            add(edge);
        }
    }

    public void add(Edge edge) {
        addNode(edge.getOriginId(), edge.getDestiny());
        if (!edge.isDirected()) {
            addNode(edge.getDestinyId(), edge.getOrigin());
        }
    }

    private void addNode(int index, Node node) {
        List<Node> adjacencyList = graph.getOrDefault(index, new ArrayList<>());
        adjacencyList.add(node);
        graph.put(index, adjacencyList);
    }

}
