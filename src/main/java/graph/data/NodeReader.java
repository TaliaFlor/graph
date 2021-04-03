package graph.data;

import graph.exception.MalformedObjectException;
import graph.model.Node;
import graph.parser.NodeParser;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class NodeReader implements DataReader<Node> {

    private final NodeParser parser = new NodeParser();

    private final String filename;
    private final int linesToSkip;
    private final char separator;


    public NodeReader(String filename, int linesToSkip, char separator) {
        this.filename = filename;
        this.linesToSkip = linesToSkip;
        this.separator = separator;
    }


    @Override
    public List<Node> read() {
        log.trace("Reading node file");

        List<Node> nodes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

            int lineNumber = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber == linesToSkip)
                    continue;

                String[] data = line.split(String.valueOf(separator));
                Node node = parser.parse(data);
                nodes.add(node);
            }

            log.info("{} nodes parsed", (lineNumber - 1));
        } catch (FileNotFoundException e) {
            log.error("Node file not found - {}", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            log.error("Input error during node file reading");
            e.printStackTrace();
        } catch (MalformedObjectException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error("Error during node file reading");
            e.printStackTrace();
        }

        log.trace("Node file read");
        return nodes;
    }

}
