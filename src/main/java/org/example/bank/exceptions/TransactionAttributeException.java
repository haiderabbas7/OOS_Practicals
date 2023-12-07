package org.example.bank.exceptions;

/**
 * Exception für den Fall, dass bei einem Transaktions-Objekt die Attribute falsch sind. (Transfer: Negativer Überweisungsbetrag, Payment: Falsche Zinssätze)
 */
public class TransactionAttributeException extends RuntimeException{
    public TransactionAttributeException(String ausgabe) {
        super(ausgabe);
    }
}
