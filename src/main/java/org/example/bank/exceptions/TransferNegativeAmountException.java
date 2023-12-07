package org.example.bank.exceptions;
/**
 * Exception für den Fall, dass für den Überweisungsbetrag bei einem Transfer-Objekt eine negative Zahl angegeben wurde
 */
public class TransferNegativeAmountException extends RuntimeException{
    public TransferNegativeAmountException(String ausgabe) {
        super(ausgabe);
    }
}
