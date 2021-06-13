package graph.dijkstra;

import graph.util.Json;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DijkstraSimpleModel {
    @PositiveOrZero
    @EqualsAndHashCode.Include
    private int nodeId;
    @PositiveOrZero
    private double totalDistance;
    @PositiveOrZero
    private int predecessorId;


    public DijkstraSimpleModel(DijkstraModel dijkstraModel) {
        this.nodeId = dijkstraModel.getNode().getId();
        this.totalDistance = dijkstraModel.getDistance();

        if (dijkstraModel.getPredecessor() == null)
            this.predecessorId = -1;
        else
            this.predecessorId = dijkstraModel.getPredecessor().getId();
    }


    @Override
    public String toString() {
        return Json.toJson(this);
    }

}
