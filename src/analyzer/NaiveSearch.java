package analyzer;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class NaiveSearch implements Strategy {
    @Override
    public void search(String fileName, List<Pattern> patterns) {
        for (var pattern : patterns) {
            boolean isPDF = false;
            try {
                byte[] allBytes = Files.readAllBytes(Paths.get(fileName));
                String tmp = new String(allBytes);
                if (tmp.contains(pattern.value())) {
                    isPDF = true;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (isPDF) {
                System.out.println();
            } else {
                System.out.println("Unknown file type");
            }
        }
    }
}
