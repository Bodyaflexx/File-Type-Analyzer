package analyzer;

import java.util.List;

public interface Strategy {
    public void search(String fileName, List<Pattern> patterns);
}
