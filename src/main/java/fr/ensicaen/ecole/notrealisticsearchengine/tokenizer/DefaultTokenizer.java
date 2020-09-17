package fr.ensicaen.ecole.notrealisticsearchengine.tokenizer;

public class DefaultTokenizer implements Tokenizer {

    @Override
    public String[] tokenize(String text) {
        return text.split("\\s+");
    }
}
