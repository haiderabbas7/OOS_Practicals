package org.example.bank;

import org.example.bank.exceptions.TransferNegativeAmountException;

/**
 * Eine Unterklasse von Transaction. Speziell für Überweisungen
 */
public abstract class Transfer extends Transaction implements CalculateBill{
    /**
     * Name des Senders
     */
    private String sender = "";
    /**
     * Name des Empfängers
     */
    private String recipient = "";


    /**
     * Ein Override der Methode setAmount der Oberklasse {@link Transaction}
     * Setzt den Wert des Klassenattributes {@link #amount} auf den Wert im Parameter.
     * Da bei Überweisungen nur positive Werte erlaubt sind, wird bei einer negativen Eingabe der Nutzer per Konsole gewarnt.
     * @param amount - der zu setzende Wert
     * @throws TransferNegativeAmountException Wenn der angegebene double-Wert für die zu überweisebde Geldmenge negativ ist
     */
    @Override
    public void setAmount(double amount) throws TransferNegativeAmountException{
        if(amount < 0){
            throw new TransferNegativeAmountException("Negative Überweisungen sind nicht möglich!");
        }
        this.amount = amount;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }



    /**
     * Erster Konstruktor der Klasse Transfer, setzt nur die ersten drei Klassenattribute
     * @param date - der zu setzende Wert
     * @param amount - der zu setzende Wert
     * @param description - der zu setzende Wert
     * @throws TransferNegativeAmountException Wenn der angegebene double-Wert für die zu überweisebde Geldmenge negativ ist
     */
    public Transfer(String date, double amount, String description) throws TransferNegativeAmountException{
        super(date, amount, description);
        this.setDate(date);
    }

    /**
     * Zweiter Konstruktor der Klasse Transfer, setzt alle Klassenattribute
     * @param date - der zu setzende Wert
     * @param amount - der zu setzende Wert
     * @param description - der zu setzende Wert
     * @param sender - der zu setzende Wert
     * @param recipient - der zu setzende Wert
     * @throws TransferNegativeAmountException Wenn der angegebene double-Wert für die zu überweisebde Geldmenge negativ ist
     */
    public Transfer(String date, double amount, String description, String sender, String recipient) throws TransferNegativeAmountException{
        this(date, amount, description);
        this.setSender(sender);
        this.setRecipient(recipient);
    }

    /**
     * Einziger Copy Constructor der Klasse Transfer
     * @param other - Das zu kopierende Element
     */
    public Transfer(Transfer other) throws TransferNegativeAmountException{
        this(other.getDate(), other.getAmount(), other.getDescription());
        this.setSender(other.getSender());
        this.setRecipient(other.getRecipient());
    }


    /**
     * Kalkuliert den richtigen Wert von {@link #amount}, da es aber keine Zinsen oder sonstige Faktoren gibt,
     * wird derselbe Wert zurückgegeben
     * @return derselbe Wert wie {@link #amount}
     */
    @Override
    public double calculate() {
        return amount;
    }

    /**
     * Ein Override der Methode der Oberklasse {@link Transaction}
     * Erzielt gleiche Funktionalität wie die Methode der Oberklasse
     * @return Ein String mit allen Klassenattributen, durch Kommas separiert
     */
    @Override
    public String toString() {
        return (super.toString() + ", " + this.getSender() + ", " + this.getRecipient());
    }


    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(obj == null || getClass() != obj.getClass()){
            return false;
        }

        if (!super.equals(obj)) {
            return false;
        }

        Transfer other = (Transfer) obj;
        return (getDate().equals(other.getDate()) &&
                getAmount() == other.getAmount() &&
                getDescription().equals(other.getDescription()) &&
                getSender().equals(other.getSender()) &&
                getRecipient().equals(other.getRecipient()));
    }
}
