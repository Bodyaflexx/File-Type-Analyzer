package analyzer;

import java.util.List;

public class SearcherStrategy {
    private Strategy method;

    public void setMethod(Strategy method) {
        this.method = method;
    }
    public void search(String fileName, List<Pattern> patterns){
        this.method.search(fileName,patterns);
    }
}
