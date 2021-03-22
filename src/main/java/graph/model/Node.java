package graph.model;

import graph.exception.MalformedObjectException;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Node {

    @Positive
    private final int id;
    private String name;
    private String color;

    @Override
    public String toString() {
        return "{\"Node\":{"
                + "\"id\":\"" + id + "\""
                + ", \"name\":\"" + name + "\""
                + ", \"color\":\"" + color + "\""
                + "}}";
    }

}
