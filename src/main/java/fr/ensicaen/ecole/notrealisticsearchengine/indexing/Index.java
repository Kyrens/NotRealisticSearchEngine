package fr.ensicaen.ecole.notrealisticsearchengine.indexing;

import fr.ensicaen.ecole.notrealisticsearchengine.tokenizer.DefaultTokenizer;
import fr.ensicaen.ecole.notrealisticsearchengine.tokenizer.Tokenizer;

import java.io.File;
import java.util.List;

public class Index {

    private Tokenizer tokenizer;

    public Index() {
        this(new DefaultTokenizer());
    }

    public Index(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public void index(Document document) {

    }

    public List<Document> search(String query) {
        return null;
    }
}
