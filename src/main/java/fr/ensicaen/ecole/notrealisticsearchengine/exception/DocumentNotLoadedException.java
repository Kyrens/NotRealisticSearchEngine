package fr.ensicaen.ecole.notrealisticsearchengine.exception;

public class DocumentNotLoadedException extends Exception {

    /**
     * @param message The error message
     */
    public DocumentNotLoadedException(String message) {
        super(message);
    }
}
