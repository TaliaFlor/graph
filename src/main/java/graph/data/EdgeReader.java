package graph.data;

import graph.exception.MalformedObjectException;
import graph.model.Edge;
import graph.model.Node;
import graph.util.EdgeParser;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class EdgeReader implements DataReader<Edge> {

    private final EdgeParser parser;

    private final String filename;
    private final int linesToSkip;
    private final char separator;


    public EdgeReader(List<Node> nodeList, String filename, int linesToSkip, char separator) {
        this.filename = filename;
        this.linesToSkip = linesToSkip;
        this.separator = separator;

        parser = new EdgeParser(nodeList);
    }


    @Override
    public List<Edge> read() {
        log.trace("Reading edge file");

        List<Edge> edges = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

            int lineNumber = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber == linesToSkip)
                    continue;

                String[] data = line.split(String.valueOf(separator));
                Edge edge = parser.parse(data);
                edges.add(edge);
            }

            log.info("{} lines read", (lineNumber - 1));
        } catch (FileNotFoundException e) {
            log.error("Edge file not found - {}", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            log.error("Input error during edge file reading");
            e.printStackTrace();
        } catch (MalformedObjectException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error("Error during edge file reading");
            e.printStackTrace();
        }

        log.trace("Edge file read");
        return edges;
    }

}
