package fr.ensicaen.ecole.notrealisticsearchengine.tokenizer;

public class DefaultTokenizer implements Tokenizer {

    @Override
    public String[] tokenize(String text) {
        String[] l = text.split("[^(A-Za-zéèàçîïëäüù\\-)]");
        for (int i = 0; i < l.length; ++i) {
            l[i] = l[i].toLowerCase();
        }
        return l;
    }
}
