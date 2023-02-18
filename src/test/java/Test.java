import io.github.racoondog.CSVWriter;
import io.github.racoondog.StaticCSVWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Test {
    public static void main(String[] args) throws IOException {
        Path file = new File("./export").getCanonicalFile().toPath().resolve("test.csv");
        Files.createDirectories(file.getParent());
        if (!Files.isRegularFile(file)) Files.createFile(file);

        try (StaticCSVWriter writer = CSVWriter.builder().separatorChar(';').escapeChar('\\').buildStatic(Files.newBufferedWriter(file, StandardOpenOption.TRUNCATE_EXISTING))) {
            writer.writeCharSeqs("A", "B", "C");
            writer.writeCharSeqs("Hello, World!", "foobar", ";;;");
        }
    }
}
