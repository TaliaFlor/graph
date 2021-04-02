package graph.model;

import graph.util.Json;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Edge {

    @Positive
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
