package graph.representation;

import graph.model.Node;
import graph.util.Json;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Relation {

    @Positive
    private final int nodeId;
    private List<Node> adjacencyList;

    @Override
    public String toString() {
        return Json.toJson(this);
    }

}
