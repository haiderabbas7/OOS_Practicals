package org.example.bank;

import org.example.bank.exceptions.*;
import java.util.*;
import java.util.Map.Entry;

public class PrivateBankAlt implements Bank{
    /**
     * Name der privaten bank
     */
    private String name;
    /**
     * Zinsen/Zinssatz der bei einer Einzahlung anfällt
     * */
    private double incomingInterest;
    /**
     * Zinsen/Zinssatz der bei einer Auszahlung anfällt
     * */
    private double outgoingInterest;
    /**
     * Verknüpft Kontos mit ihren Transaktionen zu
     * String stellt den Schlüssel für die Namen der Kontos dar
     * "Die Map accountsToTransactions soll hingegen nicht im Konstruktor, sondern direkt bei der Definition des Klassenattributs mit new erzeugt werden."
     */
    public Map<String, List<Transaction>> accountsToTransactions = new HashMap<>();

    public String getName() {
        return name;
    }

    public double getIncomingInterest() {
        return incomingInterest;
    }

    public double getOutgoingInterest() {
        return outgoingInterest;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIncomingInterest(double incomingInterest) {
        this.incomingInterest = incomingInterest;
    }

    public void setOutgoingInterest(double outgoingInterest) {
        this.outgoingInterest = outgoingInterest;
    }

    /**
     * Einziger Konstruktor der Klasse PrivateBankAlt. Setzt nur die ersten drei Klassenattribute.
     * @param name {@link #name}
     * @param incomingInterest {@link #incomingInterest}
     * @param outgoingInterest {@link #outgoingInterest}
     */
    public PrivateBankAlt(String name, double incomingInterest, double outgoingInterest){
        this.setName(name);
        this.setIncomingInterest(incomingInterest);
        this.setOutgoingInterest(outgoingInterest);
    }


    /**
     * Copy Constructor
     * @param other Das zu kopierende Objekt
     */
    public PrivateBankAlt(PrivateBank other){
        this.setName(other.getName());
        this.setIncomingInterest(other.getIncomingInterest());
        this.setOutgoingInterest(other.getOutgoingInterest());
    }


    @Override
    public String toString() {
        String ret = "Name der Bank: " + this.getName() + "\nIncoming Interest: " + this.getIncomingInterest() + "\nOutgoing Interest: " + this.getOutgoingInterest() + "\n";
        for (Entry<String, List<Transaction>> entry : accountsToTransactions.entrySet()){
            ret += "Konto '" + entry.getKey() + "' => ";
            for(Transaction t: entry.getValue()){
                ret += "(";
                if(t instanceof Payment){
                    ret += ((Payment)t).toString();
                }
                else if (t instanceof Transfer) {
                    ret += ((Transfer)t).toString();
                }
                ret += "), ";
            }
            ret += "\n";
        }
        return ret;
    }


    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(obj == null || this.getClass() != obj.getClass()){
            return false;
        }
        PrivateBank other = (PrivateBank) obj;
        return (this.getName().equals(other.getName()) &&
                this.getIncomingInterest() == other.getIncomingInterest() &&
                this.getOutgoingInterest() == other.getOutgoingInterest() &&
                this.accountsToTransactions == other.accountsToTransactions);
    }


    @Override
    public void createAccount(String account) throws AccountAlreadyExistsException {
        //Falls Map leer ist und es keinen Account mit dem gleichen Namen gibt
        if (accountsToTransactions.isEmpty() || !(accountsToTransactions.containsKey(account))) {
            List<Transaction> acc = new ArrayList<>();
            accountsToTransactions.put(account, acc);
        }
        else{
            throw new AccountAlreadyExistsException("Konto mit dem Namen '" + account + "' existiert schon!");
        }
    }


    @Override
    public void createAccount(String account, List<Transaction> transactions) throws AccountAlreadyExistsException, TransactionAlreadyExistException, TransactionAttributeException {
        if (accountsToTransactions.containsKey(account)) {
            throw new AccountAlreadyExistsException("Konto mit dem Namen '" + account + "' existiert schon!");
        }
        List<Transaction> acc = new ArrayList<>();
        accountsToTransactions.put(account, acc);
        for(Transaction transaction :  transactions){
            if(acc.isEmpty() || !(acc.contains(transaction))){
                this.addTransaction(account, transaction);
            }
            else{
                throw new TransactionAlreadyExistException("Transaktion (" + transaction.getDate() + ", " + transaction.getAmount() + ", " + transaction.getDescription() + ") wurde mehrfach angegeben!");
            }
        }

    }


    @Override
    public void addTransaction(String account, Transaction transaction) throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException {
        if (accountsToTransactions.isEmpty() || !(accountsToTransactions.containsKey(account))) {
            throw new AccountDoesNotExistException("Konto mit dem Namen '" + account + "' existiert nicht!");
        }
        if(containsTransaction(account, transaction)){
            throw new TransactionAlreadyExistException("Transaktion (" + transaction.getDate() + ", " + transaction.getAmount() + ", " + transaction.getDescription() + ") existiert schon unter dem angegebenen Konto!");
        }
        /*if(transaction instanceof Transfer && (!(((Transfer)transaction).getSender().equals(account))||!(((Transfer)transaction).getRecipient().equals(account)))){
            throw new TransactionAttributeException("Kontoname " + account + " ist nicht Teil dieser Transaktion!");
        }*/
        if(transaction instanceof Payment){
            if(this.getIncomingInterest() < 0 || this.getIncomingInterest() > 1) {
                throw new TransactionAttributeException("Für Zinssätze sind nur Werte zwischen 0 und 1 erlaubt!");
            }
            else{
                ((Payment) transaction).setIncomingInterest(this.getIncomingInterest());
            }
            if(this.getOutgoingInterest() < 0 || this.getOutgoingInterest() > 1) {
                throw new TransactionAttributeException("Für Zinssätze sind nur Werte zwischen 0 und 1 erlaubt!");
            }
            else{
                ((Payment) transaction).setOutgoingInterest(this.getOutgoingInterest());
            }
        }
        else if(transaction instanceof Transfer){
            if(((Transfer)transaction).getAmount() < 0){
                throw new TransactionAttributeException("Negative Überweisungen sind nicht möglich!");
            }
        }
        accountsToTransactions.get(account).add(transaction);
    }


    @Override
    public void removeTransaction(String account, Transaction transaction) throws AccountDoesNotExistException, TransactionDoesNotExistException {
        if (accountsToTransactions.isEmpty() || !(accountsToTransactions.containsKey(account))) {
            throw new AccountDoesNotExistException("Konto mit dem Namen '" + account + "' existiert nicht!");
        }
        if(accountsToTransactions.containsKey(account) && !containsTransaction(account, transaction)){
            throw new TransactionDoesNotExistException("Transaktion (" + transaction.toString() + ") existiert nicht!");
        }
        //fehler?
        getTransactions(account).remove(transaction);
    }


    @Override
    public boolean containsTransaction(String account, Transaction transaction) throws AccountDoesNotExistException{
        if (accountsToTransactions.isEmpty() || !(accountsToTransactions.containsKey(account))) {
            throw new AccountDoesNotExistException("Konto mit dem Namen '" + account + "' existiert nicht!");
        }
        return (accountsToTransactions.get(account).contains(transaction));
    }


    @Override
    public double getAccountBalance(String account) throws AccountDoesNotExistException{
        if (accountsToTransactions.isEmpty() || !(accountsToTransactions.containsKey(account))) {
            throw new AccountDoesNotExistException("Konto mit dem Namen '" + account + "' existiert nicht!");
        }
        double balance = 0.0;
        for(Transaction transaction : getTransactions(account)){
            if(transaction instanceof Transfer){
                if(((Transfer) transaction).getSender().equals(account)){
                    balance -= ((Transfer) transaction).calculate();
                }
                else {
                    balance += ((Transfer) transaction).calculate();
                }
            }
            else{
                balance += ((Payment) transaction).calculate();
            }
        }
        return balance;
    }


    @Override
    public List<Transaction> getTransactions(String account) throws AccountDoesNotExistException{
        if (accountsToTransactions.isEmpty() || !(accountsToTransactions.containsKey(account))) {
            throw new AccountDoesNotExistException("Konto mit dem Namen '" + account + "' existiert nicht!");
        }
        return accountsToTransactions.get(account);
    }


    @Override
    public List<Transaction> getTransactionsSorted(String account, boolean asc) throws AccountDoesNotExistException{
        if (accountsToTransactions.isEmpty() || !(accountsToTransactions.containsKey(account))) {
            throw new AccountDoesNotExistException("Konto mit dem Namen '" + account + "' existiert nicht!");
        }
        List<Transaction> ret = new ArrayList<>();
        if(!(getTransactions(account).isEmpty())){
            List<Transaction> temp = new ArrayList<>(getTransactions(account));
            Transaction vergleich = new Payment("", 0, "");
            if(asc) {
                vergleich.setAmount(Double.MAX_VALUE);
            }
            else {
                vergleich.setAmount(Double.MIN_VALUE);
            }
            int x = temp.size();
            for (int i = 0; i < x; i++){
                vergleich = temp.get(0);
                for (Transaction transaction : temp) {
                    if(asc) {
                        if (transaction.calculate() < vergleich.calculate()) {
                            vergleich = transaction;
                        }
                    }
                    else {
                        if (transaction.calculate() > vergleich.calculate()) {
                            vergleich = transaction;
                        }
                    }
                }
                temp.remove(vergleich);
                ret.add(vergleich);
            }
        }
        return ret;
    }


    @Override
    public List<Transaction> getTransactionsByType(String account, boolean positive) throws AccountDoesNotExistException{
        if (accountsToTransactions.isEmpty() || !(accountsToTransactions.containsKey(account))) {
            throw new AccountDoesNotExistException("Konto mit dem Namen '" + account + "' existiert nicht!");
        }
        List<Transaction> ret = new ArrayList<>();
        if(!(getTransactions(account).isEmpty())){
            for (Transaction transaction : getTransactions(account)) {
                if((transaction.calculate() > 0) == positive){
                    ret.add(transaction);
                }
            }
        }
        return ret;
    }
}

