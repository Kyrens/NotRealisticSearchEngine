package fr.ensicaen.ecole.notrealisticsearchengine.search;

import fr.ensicaen.ecole.notrealisticsearchengine.indexing.Document;
import fr.ensicaen.ecole.notrealisticsearchengine.indexing.Index;
import fr.ensicaen.ecole.notrealisticsearchengine.tokenizer.Tokenizer;
import org.apache.commons.lang3.tuple.Pair;

import javax.print.Doc;
import java.util.*;

import static java.lang.Math.sqrt;


public class Request {

    private String query;
    private Tokenizer tokenizer;

    private HashMap<String, Integer> request_vector;
    private ArrayList<Pair<Document, Float>> salton_coefficient;

    /**
     * Constructor of Request class.
     * @param query Request of the user
     * @param tokenizer Entity of Tokenizer class
     */
    public Request(String query, Tokenizer tokenizer) {
        this.query = query;
        this.tokenizer = tokenizer;

        this.request_vector = new HashMap<String, Integer>();
        this.salton_coefficient = new ArrayList<Pair<Document, Float>>();
    }


    /**
     * execute() method.
     * Process the query to transform it into occurences probabilities.
     */
    private void execute() {
        String[] tokens = tokenizer.tokenize(this.query);

        for (int i = 0; i < tokens.length; i++) {
            if (!this.request_vector.containsKey(tokens[i])) {
                this.request_vector.put(tokens[i], 1);
            }
        }
    }

    /**
     * salton_compute() method.
     * Compute the coefficient of Salton using a document vector and the request vector.
     * @param index
     * @param v The document vector of occurences.
     * @param words
     */
    private void salton_compute(Index index, Document v, HashSet<String> words) {
        float C = 0;
        float vr_sum = 0;
        float v2_sum = 0;
        float r2_sum = 0;

        float pij;
        int r;

        for(String word: words) {
            pij = index.getTFIDF(v, word);
            r = this.request_vector.getOrDefault(word, 0);

            vr_sum += pij * r;
            v2_sum = pij * pij;
            r2_sum += r * r;
        }

        C = vr_sum/((float)sqrt(v2_sum*r2_sum));
        this.salton_coefficient.add(Pair.of(v, C));
    }

    public Document[] result(Index index, int n) {
        this.execute();

        HashSet<String> words = index.getAllWords();
        for(Document doc: index.getDocuments()) {
            this.salton_compute(index, doc, words);
        }

        this.salton_coefficient.sort((o1, o2) -> Float.compare(o1.getValue(), o2.getValue()));
        Document[] document_list = new Document[n];
        for(int i = 0; i < n; i++) {
            document_list[i] = this.salton_coefficient.get(i).getKey();
        }

        return document_list;
    }

}
