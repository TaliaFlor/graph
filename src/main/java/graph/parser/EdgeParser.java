package graph.parser;

import graph.exception.MalformedObjectException;
import graph.model.Edge;
import graph.model.Node;

import java.util.Arrays;
import java.util.List;

public class EdgeParser implements Parser<Edge> {

    private static final int NUM_ATTRIBUTES = 4;

    private final List<Node> nodeList;


    public EdgeParser(List<Node> nodeList) {
        this.nodeList = nodeList;
    }


    public Edge parse(String[] data) {
        if (data.length != NUM_ATTRIBUTES)
            throw new MalformedObjectException("Malformed edge object structure -> " + Arrays.toString(data));

        int id = Integer.parseInt(data[0]);
        int originId = Integer.parseInt(data[1]);
        int destinyId = Integer.parseInt(data[2]);
        boolean isDirected = Boolean.parseBoolean(data[3]);

        return new Edge(id, getNode(originId), getNode(destinyId), isDirected);
    }

    private Node getNode(int nodeId) {
        Node node = null;

        if (nodeList != null && !nodeList.isEmpty())
            node = nodeList.stream()
                    .filter(obj -> obj.getId() == nodeId)
                    .findFirst()
                    .orElse(new Node(nodeId));

        if (node == null)
            node = new Node(nodeId);

        return node;
    }

}
