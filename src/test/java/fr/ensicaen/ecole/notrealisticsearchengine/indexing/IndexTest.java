package fr.ensicaen.ecole.notrealisticsearchengine.indexing;

import fr.ensicaen.ecole.notrealisticsearchengine.exception.DocumentNotLoadedException;
import fr.ensicaen.ecole.notrealisticsearchengine.tokenizer.DefaultTokenizer;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class IndexTest {

    @Test
    public void index() throws IOException, DocumentNotLoadedException {
        Document document = new Document(new File("src/main/resources/documents/Automne_GA.txt"));
        document.load(new DefaultTokenizer());
        Index idx = new Index();
        idx.index(document);
    }

    @Test
    public void search() {
    }
}
