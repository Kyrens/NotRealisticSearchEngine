package fr.ensicaen.ecole.notrealisticsearchengine.search;

import fr.ensicaen.ecole.notrealisticsearchengine.indexing.Document;
import fr.ensicaen.ecole.notrealisticsearchengine.indexing.Index;
import fr.ensicaen.ecole.notrealisticsearchengine.tokenizer.Tokenizer;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

import static java.lang.Math.sqrt;


public class Request {

    private final String query;
    private final Tokenizer tokenizer;

    private final HashMap<String, Integer> request_vector;
    private final ArrayList<Pair<Document, Float>> salton_coefficient;

    /**
     * Constructor of Request class.
     *
     * @param query Request of the user.
     * @param tokenizer Entity of Tokenizer class.
     */
    public Request(String query, Tokenizer tokenizer) {
        this.query = query;
        this.tokenizer = tokenizer;

        this.request_vector = new HashMap<>();
        this.salton_coefficient = new ArrayList<>();
    }


    /**
     * execute() method.
     * Process the query to transform it into occurrences probabilities.
     */
    private void execute() {
        String[] tokens = tokenizer.tokenize(this.query);

        for (String token : tokens) {
            if (!this.request_vector.containsKey(token)) {
                this.request_vector.put(token, 1);
            }
        }
    }

    /**
     * salton_compute() method.
     * Compute the coefficient of Salton using a document vector and the request vector.
     *
     * @param index The index of the whole set of documents.
     * @param v The document vector of occurrences.
     * @param words The set of words in all documents.
     */
    private void salton_compute(Index index, Document v, HashSet<String> words) {
        float C;
        float vr_sum = 0;
        float v2_sum = 0;
        float r2_sum = 0;

        float pij;
        int r;

        for (String word : words) {
            pij = index.getTFIDF(v, word);
            r = this.request_vector.getOrDefault(word, 0);

            vr_sum += pij * r;
            v2_sum += pij * pij;
            r2_sum += r * r;
        }

        C = vr_sum / ((float) sqrt(v2_sum * r2_sum));
        if (Float.isNaN(C)) {
            C = 0;
        }
        this.salton_coefficient.add(Pair.of(v, C));
    }

    /**
     * result() method.
     * Execute the query and return the n best documents according to the query.
     * @param index The index of the whole set of documents.
     * @param n The number of documents returned.
     * @return The list of documents requested.
     */
    public Document[] result(Index index, int n) {
        this.execute();

        HashSet<String> words = index.getAllWords();
        for (Document doc : index.getDocuments()) {
            this.salton_compute(index, doc, words);
        }

        this.salton_coefficient.sort((o1, o2) -> Float.compare(o2.getValue(), o1.getValue()));
        Document[] document_list = new Document[n];
        for (int i = 0; i < n; i++) {
            document_list[i] = this.salton_coefficient.get(i).getKey();
        }

        return document_list;
    }

}
