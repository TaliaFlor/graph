package graph.dijkstra;

import graph.model.Edge;
import graph.model.Node;
import graph.util.Json;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Contêm os atríbutos necessários para uma busca de caminhos mínimos (dijkstra)
 */
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DijkstraModel {
    public static final double INFINITE = Double.MAX_VALUE;

    @Positive
    @EqualsAndHashCode.Include
    private final Node node;
    private Status status = Status.OPEN;
    @PositiveOrZero
    private double distance = INFINITE;
    private Node predecessor;


    @Override
    public String toString() {
        return Json.toJson(this);
    }


    public boolean equalsById(int nodeId) {
        return node.getId() == nodeId;
    }

    public boolean equalsByNode(Node other) {
        return node.equals(other);
    }


    public void markAsInitialNode() {
        distance = 0;
    }

    public boolean isOpen() {
        return status == Status.OPEN;
    }

    public void closeNode() {
        status = Status.CLOSED;
    }

    public void markAsVisited(DijkstraModel predecessorModel, Edge edge) {
        double newDistance;
        if (edge != null)
            newDistance = predecessorModel.getDistance() + edge.getWeight();
        else
            newDistance = predecessorModel.getDistance();

        if (newDistance < distance) {
            distance = newDistance;
            predecessor = predecessorModel.getNode();
        }
    }


}
