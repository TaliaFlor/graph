package util;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Writer {

    @SneakyThrows
    public static void write(String data, String filename) {
        Path path = Paths.get(filename);
        byte[] dataBytes = data.getBytes();
        Files.write(path, dataBytes);
    }

}
