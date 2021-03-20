package graph.data;

import graph.model.Node;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class NodeReader implements DataReader<Node> {

    public List<Node> read(String filename) {
        log.trace("Reading node file");

        List<Node> nodes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

            int linesQtd = 0;           
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Node node = parseData(data);
                nodes.add(node);
                linesQtd++;
            }

            log.info("{} lines read from the node file", linesQtd);
        } catch (FileNotFoundException e) {
            log.error("Node file not found - {}", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            log.error("Input error during node file reading");
            e.printStackTrace();
        } catch (Exception e) {
            log.error("Error during node file reading");
            e.printStackTrace();
        }

        log.trace("Node file read");
        return nodes;
    }

    private Node parseData(String[] data) {
        int id = Integer.parseInt(data[0]);
        String name = data[1];
        String color = data[2];

        return new Node(id, name, color);
    }

}
