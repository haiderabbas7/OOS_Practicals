package org.example.bank;

/**
 * Oberklasse von Transaktions-Objekten. Unterklassen sind {@link Payment} und {@link Transfer}
 */
public abstract class Transaction implements CalculateBill{
    /**
     * Datum der Transaktion
     */
    protected String date = "";
    /**
     * Höhe der Transaktion
     */
    protected double amount = 0;
    /**
     * Beschreibung der Transaktion
     */
    protected String description = "";



    public String getDate(){
        return this.date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * Einziger Konstruktor der Klasse Transaktion, setzt nur die ersten drei Klassenattribute
     * @param date - der zu setzende Wert
     * @param amount - der zu setzende Wert
     * @param description - der zu setzende Wert
     */
    public Transaction(String date, double amount, String description) {
        this.setDate(date);
        this.setAmount(amount);
        this.setDescription(description);
    }

    /**
     * Ein Override der üblichen Methode Object.toString(), welches jedoch die gleiche Funktion erfüllt
     * @return Ein String mit allen Klassenattributen, durch Kommas separiert
     */
    @Override
    public String toString(){
        return (this.getDate() + ", " + this.calculate() + ", " + this.getDescription());
    }

    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(obj == null || this.getClass() != obj.getClass()){
            return false;
        }
        Transaction other = (Transaction) obj;
        return (date.equals(other.getDate()) &&
                amount == other.getAmount() &&
                description.equals(other.getDescription()));
    }
}

