package org.example.bank.exceptions;

/**
 * Exception für den Fall, dass beim Erstellen eines Payment-Objektes für die Zinssätze ein invalider Wert angegeben wurde
 */
public class PaymentInvalidPercentageException extends RuntimeException{
    public PaymentInvalidPercentageException(String ausgabe) {
        super(ausgabe);
    }
}

