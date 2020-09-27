package fr.ensicaen.ecole.notrealisticsearchengine;

import fr.ensicaen.ecole.notrealisticsearchengine.exception.DocumentNotLoadedException;
import fr.ensicaen.ecole.notrealisticsearchengine.indexing.Document;
import fr.ensicaen.ecole.notrealisticsearchengine.indexing.Index;
import fr.ensicaen.ecole.notrealisticsearchengine.search.Request;
import fr.ensicaen.ecole.notrealisticsearchengine.tokenizer.DefaultTokenizer;
import fr.ensicaen.ecole.notrealisticsearchengine.tokenizer.Tokenizer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, DocumentNotLoadedException {

        printGreetings();

        Scanner sc = new Scanner(System.in);
        Index index = new Index();
        Tokenizer tokenizer = new DefaultTokenizer();
        File documentsFolder = new File("src/main/resources/documents/");

        System.out.println(loadDocuments(documentsFolder, tokenizer, index) + " documents chargés.\n");

        System.out.print("Termes à rechercher: ");
        while(sc.hasNext()) {
            String s = sc.nextLine();
            if(s.equals("!exit")) {
                break;
            }
            Request req = new Request(s, tokenizer);
            System.out.println("3 meilleurs résultats:");
            for (Document doc : req.result(index, 3)) {
                System.out.println("- " + doc.getId());
            }
            System.out.println();

            System.out.print("Termes à rechercher: ");
        }
    }

    public static void printGreetings() {
        System.out.println("***********************************************");
        System.out.println("*  Bienvenue sur le moteur de recherche NRSE  *");
        System.out.println("*          Pour quitter, tapez !exit          *");
        System.out.println("***********************************************");
    }

    public static int loadDocuments(File documentsFolder, Tokenizer tokenizer, Index index) throws IOException, DocumentNotLoadedException {
        int documentsLoadedCount = 0;
        for (File f : Objects.requireNonNull(documentsFolder.listFiles())) {
            if (f.isFile()) {
                Document document = new Document(f);
                document.load(tokenizer, StandardCharsets.ISO_8859_1);
                index.index(document);
                documentsLoadedCount++;
            }
        }
        return documentsLoadedCount;
    }
}
