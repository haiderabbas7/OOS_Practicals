package org.example.bank.exceptions;

/**
 * Exception für den Fall, dass eine Transaktion nicht existiert
 */
public class TransactionDoesNotExistException extends RuntimeException{
    public TransactionDoesNotExistException(String ausgabe) {
        super(ausgabe);
    }
}