package graph.model;

import graph.util.Json;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Node {

    @Positive
    @EqualsAndHashCode.Include
    private final int id;
    private String name;
    private String color;

    @Override
    public String toString() {
        return Json.toJson(this);
    }

    public boolean equalsById(int otherId) {
        return id == otherId;
    }

}
