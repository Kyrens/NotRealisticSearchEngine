package fr.ensicaen.ecole.notrealisticsearchengine.tokenizer;

public interface Tokenizer {

    /**
     * Tokenize the given text
     * @param text The text
     * @return A list of tokens
     */
    String[] tokenize(String text);
}
