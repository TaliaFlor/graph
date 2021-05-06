package graph.parser;

import graph.exception.MalformedObjectException;
import graph.model.Edge;
import graph.model.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class EdgeParser implements Parser<Edge> {
    private static final int MIN_NUM_ATTRIBUTES = 4;
    private static final int MAX_NUM_ATTRIBUTES = 6;
    private static final char POINTS_OF_INTEREST_SEPARATOR = '|';

    private final List<Node> nodes;


    public EdgeParser(List<Node> nodes) {
        this.nodes = nodes;
    }


    public Edge parse(String[] data) {
        if (data.length < MIN_NUM_ATTRIBUTES || data.length > MAX_NUM_ATTRIBUTES)
            throw new MalformedObjectException("Malformed edge object structure -> " + Arrays.toString(data));

        int id = Integer.parseInt(data[0]);
        int originId = Integer.parseInt(data[1]);
        int destinyId = Integer.parseInt(data[2]);
        boolean isDirected = Boolean.parseBoolean(data[3]);
        double weight = Double.parseDouble(data[4]);
        List<String> pointsOfInterest = getPointsOfInterest(data[5]);

        return new Edge(id, getNode(originId), getNode(destinyId), isDirected, weight, pointsOfInterest);
    }

    private Node getNode(int nodeId) {
        Node node = null;

        if (nodes != null && !nodes.isEmpty())
            node = nodes.stream()
                    .filter(obj -> obj.getId() == nodeId)
                    .findFirst()
                    .orElse(new Node(nodeId));

        if (node == null)
            node = new Node(nodeId);

        return node;
    }

    private List<String> getPointsOfInterest(String data) {
        String[] pointsOfInterestArray = data.split(String.valueOf(POINTS_OF_INTEREST_SEPARATOR));

        List<String> pointsOfInterest = new ArrayList<>();

        StringBuilder builder = new StringBuilder();
        for (String c : pointsOfInterestArray) {
            if(!c.equals("|"))
                builder.append(c);
            else {
                pointsOfInterest.add(builder.toString());
                builder = new StringBuilder();
            }
        }

        return pointsOfInterest;
    }

}
