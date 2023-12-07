package org.example.bank.exceptions;

/**
 * Exception für den Fall, dass ein Account nicht existiert
 */
public class AccountDoesNotExistException extends RuntimeException{
    public AccountDoesNotExistException(String ausgabe){
        super(ausgabe);
    }
}
