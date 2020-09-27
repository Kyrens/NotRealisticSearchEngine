package fr.ensicaen.ecole.notrealisticsearchengine.indexing;

import fr.ensicaen.ecole.notrealisticsearchengine.tokenizer.Tokenizer;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.mutable.MutableInt;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Set;

public class Document {

    /**
     * The id of the document
     */
    private final String id;
    /**
     * The occurrences of the words in the document
     */
    private final HashMap<String, MutableInt> wordsOccurrences = new HashMap<>();
    /**
     * The document file
     */
    private final File file;
    /**
     * If the document has been loaded
     */
    private boolean isLoaded = false;

    /**
     * Constructor
     * @param file The file
     */
    public Document(File file) {
        this.id = file.getAbsolutePath();
        this.file = file;
    }

    /**
     * @return The id of the document
     */
    public String getId() {
        return id;
    }

    /**
     * @return If the document is loaded
     */
    public boolean isLoaded() {
        return isLoaded;
    }

    /**
     * Loads the document
     * @param tokenizer The tokenizer
     * @param charset The charset
     * @throws IOException If an error occurs
     */
    public void load(Tokenizer tokenizer, Charset charset) throws IOException {
        String[] words = tokenizer.tokenize(FileUtils.readFileToString(file, charset));
        for (String word : words) {
            if (!word.isEmpty()) {
                MutableInt count = wordsOccurrences.get(word);
                if (count == null) {
                    wordsOccurrences.put(word, new MutableInt(0));
                } else {
                    count.increment();
                }
            }
        }
        isLoaded = true;
    }

    /**
     * Loads the document in UTF-8 by default
     * @param tokenizer The tokenizer
     * @throws IOException If an exception occurs
     */
    public void load(Tokenizer tokenizer) throws IOException {
        this.load(tokenizer, StandardCharsets.UTF_8);
    }

    /**
     * Returns the TFIDF factor of the word given in the document
     * @param index The index
     * @param word The word
     * @return The TFIDF factor
     */
    public float getTFIDF(Index index, String word) {
        long docCount = index.getDocuments().stream().filter(document -> document.hasWord(word)).count();
        return (float) getWordOccurrence(word) * (float) Math.log((float) index.getDocuments().size() / (float) docCount);
    }

    /**
     * @param word The word
     * @return If the document contains the given word
     */
    public boolean hasWord(String word) {
        return wordsOccurrences.containsKey(word);
    }

    /**
     * Get the number of occurrence of the given word in the document
     * @param word The word
     * @return The number of occurrences
     */
    public int getWordOccurrence(String word) {
        MutableInt i = wordsOccurrences.get(word);
        if (i == null) {
            return 0;
        }
        return i.getValue();
    }

    /**
     * The list of words in the document
     * @return The words
     */
    public Set<String> getWords() {
        return wordsOccurrences.keySet();
    }
}
