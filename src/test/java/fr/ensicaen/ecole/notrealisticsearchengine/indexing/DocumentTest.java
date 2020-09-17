package fr.ensicaen.ecole.notrealisticsearchengine.indexing;

import fr.ensicaen.ecole.notrealisticsearchengine.tokenizer.DefaultTokenizer;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class DocumentTest {

    @Test
    public void load() throws IOException {
        Document document = new Document(new File("src/main/resources/documents/Automne_GA.txt"));
        document.load(new DefaultTokenizer());
        assertTrue(document.isLoaded());
    }
}
