package graph.util;

import graph.exception.MalformedObjectException;
import graph.model.Node;

import java.util.Arrays;

public class NodeParser implements Parser<Node>{

    public static final int NUM_ATTRIBUTES = 3;

    @Override
    public Node parse(String[] data) {
            if (data.length != NUM_ATTRIBUTES)
                throw new MalformedObjectException("Malformed node object structure -> " + Arrays.toString(data));

            int id = Integer.parseInt(data[0]);
            String name = data[1];
            String color = data[2];

            return new Node(id, name, color);
    }

}
