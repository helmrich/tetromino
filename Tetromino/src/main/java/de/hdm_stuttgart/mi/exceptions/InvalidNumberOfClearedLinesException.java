package de.hdm_stuttgart.mi.exceptions;

public class InvalidNumberOfClearedLinesException extends Exception {
    
    private static final long serialVersionUID = 1216730957428829593L;

    public InvalidNumberOfClearedLinesException(int invalidNumberOfLines) {
        super("Invalid number of cleared lines: " + invalidNumberOfLines);
    }
}
