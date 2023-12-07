package org.example.bank;

import org.example.bank.exceptions.TransactionAttributeException;

/**
 * Spezielle Unterklasse der Oberklasse Transfer für eingehende Überweisungen
 */
public class IncomingTransfer extends Transfer{

    /**
     * Erster Konstruktor der Klasse IncomingTransfer, setzt nur die ersten drei Klassenattribute
     * @param date        - der zu setzende Wert
     * @param amount      - der zu setzende Wert
     * @param description - der zu setzende Wert
     */
    public IncomingTransfer(String date, double amount, String description) throws TransactionAttributeException {
        super(date, amount, description);
    }

    /**
     * Zweiter Konstruktor der Klasse IncomingTransfer, setzt alle Klassenattribute
     * @param date        - der zu setzende Wert
     * @param amount      - der zu setzende Wert
     * @param description - der zu setzende Wert
     * @param sender      - der zu setzende Wert
     * @param recipient   - der zu setzende Wert
     */
    public IncomingTransfer(String date, double amount, String description, String sender, String recipient) throws TransactionAttributeException {
        super(date, amount, description, sender, recipient);
    }

    /**
     * Einziger Copy Constructor der Klasse IncomingTransfer
     * @param other - Das zu kopierende Element
     */
    public IncomingTransfer(Transfer other) throws TransactionAttributeException {
        super(other);
    }
}

