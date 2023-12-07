package org.example.bank;

import org.example.bank.exceptions.PaymentInvalidPercentageException;

/**
 * Eine Unterklasse von Transaction. Speziell für Einzahlung/Auszahlungen
 */
public class Payment extends Transaction {
    /**
     * Der Zinssatz bei einer Einzahlung
     */
    private double incomingInterest = 0;
    /**
     * Der Zinssatz bei einer Auszahlung
     */
    private double outgoingInterest = 0;



    public double getIncomingInterest() {
        return incomingInterest;
    }
    /**
     * Setzt den Wert des Klassenattributes {@link #incomingInterest} auf den Wert im Parameter
     * @param incomingInterest - der zu setzende Wert
     */
    public void setIncomingInterest(double incomingInterest) throws PaymentInvalidPercentageException{
        if(incomingInterest < 0 || incomingInterest > 1) {
            throw new PaymentInvalidPercentageException("Für Zinssätze sind nur Werte zwischen 0 und 1 erlaubt!");
        }
        this.incomingInterest = incomingInterest;
    }

    public double getOutgoingInterest() {
        return outgoingInterest;
    }

    /**
     * Setzt den Wert des Klassenattributes {@link #outgoingInterest} auf den Wert im Parameter
     * @param outgoingInterest - der zu setzende Wert
     * @throws PaymentInvalidPercentageException Wenn der gegebene double-Parameter für den Prozentsatz kleiner 0 oder größer 1 ist
     */
    public void setOutgoingInterest(double outgoingInterest) throws PaymentInvalidPercentageException {
        if(outgoingInterest < 0 || outgoingInterest > 1) {
            throw new PaymentInvalidPercentageException("Für Zinssätze sind nur Werte zwischen 0 und 1 erlaubt!");
        }
        this.outgoingInterest = outgoingInterest;
    }


    /**
     * Erster Konstruktor der Klasse Payment, setzt nur die ersten drei Klassenattribute
     * @param date - der zu setzende Wert
     * @param amount - der zu setzende Wert
     * @param description - der zu setzende Wert
     */
    public Payment(String date, double amount, String description) {
        super(date, amount, description);
    }

    /**
     * Zweiter Konstruktor der Klasse Payment, setzt alle Klassenattribute
     * @param date - der zu setzende Wert
     * @param amount - der zu setzende Wert
     * @param description - der zu setzende Wert
     * @param incomingInterest - der zu setzende Wert
     * @param outgoingInterest - der zu setzende Wert
     * @throws PaymentInvalidPercentageException Wenn einer der gegebenen double-Parameter für die Prozentsätze kleiner 0 oder größer 1 ist
     */
    public Payment(String date, double amount, String description, double incomingInterest, double outgoingInterest) throws PaymentInvalidPercentageException{
        this(date, amount, description);
        this.setIncomingInterest(incomingInterest);
        this.setOutgoingInterest(outgoingInterest);
    }

    /**
     * Einziger Copy Constructor der Klasse Payment
     * @param other - Das zu kopierende Element
     * @throws PaymentInvalidPercentageException Wenn einer der gegebenen double-Parameter für die Prozentsätze kleiner 0 oder größer 1 ist
     */
    public Payment(Payment other) throws PaymentInvalidPercentageException{
        this(other.getDate(), other.getAmount(), other.getDescription());
        this.setIncomingInterest(other.getIncomingInterest());
        this.setOutgoingInterest(other.getOutgoingInterest());
    }


    /**
     * Ein Override der Methode der Oberklasse {@link Transaction}
     * Kalkuliert den tatsächlichen Betrag der Zahlung, welcher durch die jeweiligen Zinsen beeinflusst wird.
     * @return Der berechnete Wert für {@link #amount}
     */
    @Override
    public double calculate() {
        double a = 0;
        if(getAmount() > 0){
            a = getAmount() - (getAmount() * getIncomingInterest());
        }
        else if (getAmount() < 0) {
            a = getAmount() + (getAmount() * getOutgoingInterest());
        }
        return a;
    }

    /**
     * Ein Override der Methode der Oberklasse {@link Transaction}
     * Erzielt gleiche Funktionalität wie die Methode der Oberklasse
     * @return Ein String mit allen Klassenattributen, durch Kommas separiert
     */
    @Override
    public String toString(){
        return (super.toString() + ", " + this.getIncomingInterest() + ", " + this.getOutgoingInterest());
    }

    /**
     * Ein Override der Methode der Oberklasse {@link Transaction}
     * Erzielt gleiche Funktionalität wie die Methode der Oberklasse
     * @param obj - Das zu vergleichende Objekt
     * @return true, wenn das Objekt gleich ist. Sonst false
     */
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

        Payment other = (Payment) obj;
        return (getDate().equals(other.getDate()) &&
                getAmount() == other.getAmount() &&
                getDescription().equals(other.getDescription()) &&
                getIncomingInterest() == other.getIncomingInterest() &&
                getOutgoingInterest() == other.getOutgoingInterest());
    }
}

