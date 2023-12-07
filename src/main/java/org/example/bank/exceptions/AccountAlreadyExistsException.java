package org.example.bank.exceptions;

/**
 * Exception für den Fall, dass ein Account schon existiert
 */
public class AccountAlreadyExistsException extends RuntimeException{
    public AccountAlreadyExistsException(String ausgabe){
        super(ausgabe);
    }
}
