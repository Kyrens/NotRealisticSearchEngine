package fr.ensicaen.ecole.notrealisticsearchengine.indexing;

import fr.ensicaen.ecole.notrealisticsearchengine.exception.DocumentNotLoadedException;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class Index {

    /**
     * The list of documents
     */
    private final HashMap<String, Document> documents = new HashMap<>();

    /**
     * Default constructor
     */
    public Index() {
    }

    /**
     * Indexes a document. The document must be loaded first
     * @param document The document
     * @throws DocumentNotLoadedException Thrown when a document is indexed without being loaded first
     */
    public void index(Document document) throws DocumentNotLoadedException {
        if (!document.isLoaded()) {
            throw new DocumentNotLoadedException("Document must be loaded before indexing");
        }
        documents.put(document.getId(), document);
    }

    /**
     * @return A collection of the indexed documents
     */
    public Collection<Document> getDocuments() {
        return documents.values();
    }

    /**
     * Get the TFIDF factor of a word in a document
     * @param doc The document
     * @param word The word
     * @return The factor
     */
    public float getTFIDF(Document doc, String word) {
        return doc.getTFIDF(this, word);
    }

    /**
     * @return The list of all words of all indexed documents
     */
    public HashSet<String> getAllWords() {
        HashSet<String> words = new HashSet<>();
        for (Document doc : getDocuments()) {
            words.addAll(doc.getWords());
        }
        return words;
    }
}
