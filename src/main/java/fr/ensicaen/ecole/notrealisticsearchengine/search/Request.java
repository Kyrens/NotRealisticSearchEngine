package fr.ensicaen.ecole.notrealisticsearchengine.search;

import fr.ensicaen.ecole.notrealisticsearchengine.tokenizer.Tokenizer;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sqrt;


public class Request {

    private String query;
    private float[] request_vector;
    private Tokenizer tokenizer;

    private ArrayList<Float> salton_coefficient;

    /**
     * Constructor of Request class.
     * @param query Request of the user
     * @param tokenizer Entity of Tokenizer class
     */
    public Request(String query, Tokenizer tokenizer) {
        this.query = query;
        this.tokenizer = tokenizer;
    }


    /**
     * execute() method.
     * Process the query to transform it into occurences probabilities.
     */
    public void execute() {
        String[] tokens = tokenizer.tokenize(this.query);
        /*A reprendre*/
    }

    /**
     * salton_compute() method.
     * Compute the coefficient of Salton using a document vector and the request vector.
     * @param v The document vector of occurences.
     * @param r The request vector of occurences.
     */
    public void salton_compute(float[] v, float[] r) {
        int n = v.length;
        float C = 0;
        float vr_sum = 0;
        float v2_sum = 0;
        float r2_sum = 0;

        for(int i = 0; i < n; i++) {
            vr_sum += v[n]*r[n];
            v2_sum += v[n]*v[n];
            r2_sum += r[n]*r[n];
        }

        C = vr_sum/((float)sqrt(v2_sum*r2_sum));
        this.salton_coefficient.add(C);
    }

}
