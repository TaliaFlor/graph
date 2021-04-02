package graph.representation;

import graph.model.Edge;
import graph.model.Node;
import graph.util.Json;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
public class Graph {    //TODO atributo com a quantidade de vértices e arestas

//    private int nodes;
//    private int edges;
    private final Map<Integer, List<Node>> graph = new HashMap<>();     //Transformar isso num objecto e lista de objetos


    @Override
    public String toString() {
        return Json.toJson(this);
    }


    public List<Node> get(int nodeId) {
        return graph.get(nodeId);
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

    private void addNode(int nodeId, Node node) {
        List<Node> adjacencyList = graph.getOrDefault(nodeId, new ArrayList<>());
        adjacencyList.add(node);
        graph.put(nodeId, adjacencyList);
    }

}
