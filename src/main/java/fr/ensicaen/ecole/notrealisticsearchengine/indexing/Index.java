package fr.ensicaen.ecole.notrealisticsearchengine.indexing;

import fr.ensicaen.ecole.notrealisticsearchengine.exception.DocumentNotLoadedException;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class Index {

    private final HashMap<String, Document> documents = new HashMap<>();

    public Index() {}

    public void index(Document document) throws DocumentNotLoadedException {
        if (!document.isLoaded()) {
            throw new DocumentNotLoadedException("Document must be loaded before indexing");
        }
        documents.put(document.getId(), document);
    }

    public Collection<Document> getDocuments() {
        return documents.values();
    }

    public float getTFIDF(Document doc, String word) {
        return doc.getTFIDF(this, word);
    }

    public HashSet<String> getAllWords() {
        HashSet<String> words = new HashSet<>();
        for (Document doc : getDocuments()) {
            words.addAll(doc.getWords());
        }
        return words;
    }
}
