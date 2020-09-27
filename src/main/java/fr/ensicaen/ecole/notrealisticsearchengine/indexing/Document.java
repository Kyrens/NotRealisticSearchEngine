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

    private final String id;
    private final HashMap<String, MutableInt> wordsOccurrences = new HashMap<>();
    private File file;
    private boolean isLoaded = false;

    public Document(File file) {
        this.id = file.getAbsolutePath();
        this.file = file;
    }

    public String getId() {
        return id;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

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

    public void load(Tokenizer tokenizer) throws IOException {
        this.load(tokenizer, StandardCharsets.UTF_8);
    }

    public float getTFIDF(Index index, String word) {
        long docCount = index.getDocuments().stream().filter(document -> document.hasWord(word)).count();
        return (float) getWordOccurrence(word) * (float) Math.log((float) index.getDocuments().size() / (float) docCount);
    }

    public boolean hasWord(String word) {
        return wordsOccurrences.containsKey(word);
    }

    public int getWordOccurrence(String word) {
        MutableInt i = wordsOccurrences.get(word);
        if (i == null) {
            return 0;
        }
        return i.getValue();
    }

    public Set<String> getWords() {
        return wordsOccurrences.keySet();
    }
}
