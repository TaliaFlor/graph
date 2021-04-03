package graph.model;

import graph.util.Json;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Edge {

    @Positive
    @EqualsAndHashCode.Include
    private final int id;
    private final Node origin;
    private final Node destiny;
    private final boolean isDirected;


    @Override
    public String toString() {
        return Json.toJson(this);
    }


    public int getOriginId() {
        return origin.getId();
    }

    public int getDestinyId() {
        return destiny.getId();
    }

}
