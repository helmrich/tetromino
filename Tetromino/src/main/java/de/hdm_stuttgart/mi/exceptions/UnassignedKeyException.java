package de.hdm_stuttgart.mi.exceptions;

public class UnassignedKeyException extends Exception {

    private static final long serialVersionUID = 2161973375478635156L;

    public UnassignedKeyException(String errorMessage) {
        super(errorMessage);
    }
}
