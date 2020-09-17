package fr.ensicaen.ecole.notrealisticsearchengine.indexing;

import fr.ensicaen.ecole.notrealisticsearchengine.exception.DocumentNotLoadedException;

import java.util.HashMap;
import java.util.List;

public class Index {

    private final HashMap<String, Document> documents = new HashMap<>();

    public Index() {}

    public void index(Document document) throws DocumentNotLoadedException {
        if (!document.isLoaded()) {
            throw new DocumentNotLoadedException("Document must be loaded before indexing");
        }
        documents.put(document.getId(), document);
    }

    public List<Document> search(String query) {
        return null;
    }
}
