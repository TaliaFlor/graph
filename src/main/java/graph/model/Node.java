package graph.model;

import com.google.gson.Gson;
import graph.exception.MalformedObjectException;
import graph.util.Json;
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
        return Json.toJson(new Node(id, name, color));
    }

}
