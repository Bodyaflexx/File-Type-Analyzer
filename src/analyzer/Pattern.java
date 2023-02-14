package analyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public record Pattern(int priority, String value, String name) {
    private static Pattern parsePattern(String s) {
        String[] parts = s.split(";");
        int priority = Integer.parseInt(parts[0]);
        String value = parts[1].replace("\"", "");
        String name = parts[2].replace("\"", "");
        return new Pattern(priority, value, name);
    }

    public static List<Pattern> readPatterns(String fileName) throws IOException {
        return Files.lines(Paths.get(fileName))
                .map(Pattern::parsePattern)
                .collect(Collectors.toList());
    }
}

