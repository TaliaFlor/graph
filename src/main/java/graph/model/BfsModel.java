package graph.model;

import graph.util.Json;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Contêm os atríbutos necessários para uma busca em extensão (breadth-first search)
 */
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BfsModel {
    public static final int INFINITE = 2147483647;  //Maior valor inteiro possível em Java

    @Positive
    @EqualsAndHashCode.Include
    private final Node node;
    private Color color = Color.WHITE;
    @PositiveOrZero
    private int distance = INFINITE;
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

    /**
     * <p>
     * Changes color from 'WHITE' to 'GREY' and sets distance to zero.
     * </p>
     */
    public void markAsInitialNode() {
        color = Color.GREY;
        distance = 0;
    }

    public boolean isNotExplored() {
        return color == Color.WHITE;
    }

    /**
     * <p>
     * Changes color from 'WHITE' to 'GREY', sets distance as predecessor distance + 1,
     * and sets predecessor node;
     * </p>
     */
    public void markAsFirstVisited(BfsModel predecessorModel) {
        color = Color.GREY;
        distance = predecessorModel.getDistance() + 1;
        predecessor = predecessorModel.getNode();
    }

    /**
     * <p>
     * Changes color from 'GREY' to 'BLACK'
     * </p>
     */
    public void markAsExplored() {
        color = Color.BLACK;
    }

}
