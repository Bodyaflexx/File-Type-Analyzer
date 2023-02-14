package analyzer;


import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Program {
    private final File file;
    private final List<Pattern> patterns;
    private String algorithm = "";

    public Program(String fileName, String patternFile) {
        this.file = new File(fileName);
        try {
            patterns = Pattern.readPatterns(patternFile);
            patterns.sort(Comparator.comparingInt(Pattern::priority).reversed());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startProgram() {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        SearcherStrategy search = new SearcherStrategy();
        switch (algorithm){
            case "--naive" -> search.setMethod(new NaiveSearch());
            case "--KMP" -> search.setMethod(new KMPSearch());
            default -> search.setMethod(new RabinKarpSearch());
        }
        for (File f : Objects.requireNonNull(file.listFiles())) {
                executor.submit(() -> search.search(f.getPath(), patterns));
        }
        try {
            executor.shutdown();
            executor.awaitTermination(60, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
