package org.example.bank.exceptions;

/**
 * Exception für den Fall, dass eine Transaktion schon existiert
 */
public class TransactionAlreadyExistException extends RuntimeException{
    public TransactionAlreadyExistException(String ausgabe) {
        super(ausgabe);
    }
}
