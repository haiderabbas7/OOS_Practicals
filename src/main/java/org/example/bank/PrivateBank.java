package org.example.bank;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.example.bank.exceptions.*;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class PrivateBank implements Bank{
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
    /**
     * Pfad der JSON-Files
     */
    private String directoryName;



    public String getName() {
        return name;
    }

    public double getIncomingInterest() {
        return incomingInterest;
    }

    public double getOutgoingInterest() {
        return outgoingInterest;
    }

    public String getDirectoryName(){
        return this.directoryName;
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

    public void setDirectoryName(String directoryName){
        this.directoryName = directoryName;
    }

    /**
     * Setzt automatisch den directoryName. Benutzt dazu den Systempfad.
     */
    public void setDirectoryNameAutomatically() {
        this.setDirectoryName(System.getProperty("user.dir") + "\\src\\main\\java\\org\\example\\JSON-Files");
    }

    /**
     * Einziger Konstruktor der Klasse PrivateBankAlt. Setzt nur die ersten drei Klassenattribute.
     * @param name {@link #name}
     * @param incomingInterest {@link #incomingInterest}
     * @param outgoingInterest {@link #outgoingInterest}
     */
    public PrivateBank(String name, double incomingInterest, double outgoingInterest) throws IOException {
        this.setName(name);
        this.setIncomingInterest(incomingInterest);
        this.setOutgoingInterest(outgoingInterest);
        setDirectoryNameAutomatically();
    }

    /**
     * Copy Constructor
     * @param other Das zu kopierende Objekt
     */
    public PrivateBank(PrivateBank other){
        this.setName(other.getName());
        this.setIncomingInterest(other.getIncomingInterest());
        this.setOutgoingInterest(other.getOutgoingInterest());
        this.setDirectoryName(other.directoryName);
        this.accountsToTransactions = other.accountsToTransactions;
    }



    @Override
    public String toString() {
        String ret = "Name der Bank: " + this.getName() + "\nIncoming Interest: " + this.getIncomingInterest() + "\nOutgoing Interest: " + this.getOutgoingInterest() + "\n" + this.getDirectoryName() + "\n";
        for (Entry<String, List<Transaction>> entry : accountsToTransactions.entrySet()){
            ret += "Konto '" + entry.getKey() + "' => ";
            for(Transaction t: entry.getValue()){
                ret += "(" + t.toString() + ")";
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
    public void createAccount(String account)
            throws AccountAlreadyExistsException, IOException {
        if (accountsToTransactions.containsKey(account)) {
            throw new AccountAlreadyExistsException("Konto mit dem Namen '" + account + "' existiert schon!");
        }
        List<Transaction> acc = new ArrayList<>();
        accountsToTransactions.put(account, acc);
        writeAccount(account);
    }


    @Override
    public void createAccount(String account, List<Transaction> transactions)
            throws AccountAlreadyExistsException, TransactionAlreadyExistException, TransactionAttributeException, IOException {
        if (accountsToTransactions.containsKey(account)) {
            throw new AccountAlreadyExistsException("Konto mit dem Namen '" + account + "' existiert schon!");
        }
        List<Transaction> acc = new ArrayList<>();
        accountsToTransactions.put(account, acc);
        writeAccount(account);
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
    public void addTransaction(String account, Transaction transaction)
            throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException, IOException {
        if (accountsToTransactions.isEmpty() || !(accountsToTransactions.containsKey(account))) {
            throw new AccountDoesNotExistException("Konto mit dem Namen '" + account + "' existiert nicht!");
        }
        if(containsTransaction(account, transaction)){
            throw new TransactionAlreadyExistException("Transaktion (" + transaction.getDate() + ", " + transaction.getAmount() + ", " + transaction.getDescription() + ") existiert schon unter dem angegebenen Konto!");
        }
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
        writeAccount(account);
    }






    @Override
    public void removeTransaction(String account, Transaction transaction)
            throws AccountDoesNotExistException, TransactionDoesNotExistException, IOException {
        if (accountsToTransactions.isEmpty() || !(accountsToTransactions.containsKey(account))) {
            throw new AccountDoesNotExistException("Konto mit dem Namen '" + account + "' existiert nicht!");
        }
        if(accountsToTransactions.containsKey(account) && !containsTransaction(account, transaction)){
            throw new TransactionDoesNotExistException("Transaktion (" + transaction.toString() + ") existiert nicht!");
        }
        getTransactions(account).remove(transaction);
        writeAccount(account);
    }


    @Override
    public boolean containsTransaction(String account, Transaction transaction)
            throws AccountDoesNotExistException{
        if (accountsToTransactions.isEmpty() || !(accountsToTransactions.containsKey(account))) {
            throw new AccountDoesNotExistException("Konto mit dem Namen '" + account + "' existiert nicht!");
        }
        return (accountsToTransactions.get(account).contains(transaction));
    }


    @Override
    public double getAccountBalance(String account)
            throws AccountDoesNotExistException{
        if (accountsToTransactions.isEmpty() || !(accountsToTransactions.containsKey(account))) {
            throw new AccountDoesNotExistException("Konto mit dem Namen '" + account + "' existiert nicht!");
        }
        double balance = 0.0;
        for(Transaction transaction : getTransactions(account)){
            balance += transaction.calculate();
        }
        return balance;
    }


    @Override
    public List<Transaction> getTransactions(String account)
            throws AccountDoesNotExistException{
        if (accountsToTransactions.isEmpty() || !(accountsToTransactions.containsKey(account))) {
            throw new AccountDoesNotExistException("Konto mit dem Namen '" + account + "' existiert nicht!");
        }
        return accountsToTransactions.get(account);
    }


    @Override
    public List<Transaction> getTransactionsSorted(String account, boolean asc)
            throws AccountDoesNotExistException{
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
    public List<Transaction> getTransactionsByType(String account, boolean positive)
            throws AccountDoesNotExistException{
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

    /**
     * Liest und deserialisiert die Objekte Transaction aus den .json Dateien
     * Und legt dafür in der Map neue Kontos und Transaktionen an
     */
    public void readAccounts(){
        //jasons ist ein Array aller .json Dateien im Ordner
        File folder = new File(directoryName);
        File[] jasons = folder.listFiles();

        //Erstellt den Gson für Transaction Objekte und Unterklassen und benutzt dafür den Custom Deserialisierer
        Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(Transaction.class, new Serialisierer()).create();

        try {
            for (File file : jasons) {
                //Erstellt in der Map neue Keys für die Kontos anhand der Dateinamen, falls noch nicht existiert
                String account = file.getName().replace(".json", "");
                if(!accountsToTransactions.containsKey(account)) {
                    accountsToTransactions.put(account, new ArrayList<>());
                }
                try{
                    //Liest alle Transaction Objekte aus den Dateien und schreibt diese in die Map, falls noch nicht drin
                    Reader reader = new FileReader(directoryName+"/"+file.getName());
                    Type transactionListType = new TypeToken<List<Transaction>>() {}.getType();
                    List<Transaction> transactionList = gson.fromJson(reader, transactionListType);

                    for(Transaction transaction : transactionList ){
                        if(!containsTransaction(account, transaction)) {
                            accountsToTransactions.get(account).add(transaction);
                        }
                    }
                    reader.close();
                }
                catch(IOException e){
                    System.out.println("Fehler beim Ein-/Auslesen.");
                }
            }
        }
        catch (NullPointerException e){
            System.out.println("Es wurden bisher noch keine Kontos und dazugehörige Transaktionen hinterlegt.");
        }
    }

    /**
     * Serialisiert und speichert das im Parameter angegebene Konto in einer neuen .json Datei, falls es existiert
     * @param account Das zu serialisierende Konto
     */
    public void writeAccount (String account) throws AccountDoesNotExistException{
        if (accountsToTransactions.isEmpty() || !(accountsToTransactions.containsKey(account))) {
            throw new AccountDoesNotExistException("Konto mit dem Namen '" + account + "' existiert nicht!");
        }
        try {
            //Erstellt den Gson für Transaction Objekte und Unterklassen und benutzt dafür den Custom Serialisierer
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeHierarchyAdapter(Transaction.class, new Serialisierer());
            Gson customGson = gsonBuilder.setPrettyPrinting().create();

            //Erstellt ein JsonArray für die eckigen Klammern und fügt alle Transaktionen erneut ein
            JsonArray jsonArray = new JsonArray();
            for (Transaction t : getTransactions(account)) {
                jsonArray.add(customGson.toJsonTree(t));
            }

            //Formatiert um in einen String, erstellt eine neue .json Datei im Dateipfad und schreibt den String ein
            String prettyJson = customGson.toJson(jsonArray);
            FileOutputStream fos = new FileOutputStream(directoryName + "\\" + account + ".json");
            fos.write(prettyJson.getBytes());
            fos.close();
        }
        catch (IOException e) {
            System.out.println("Fehler beim Ein-/Auslesen.");
        }
    }
}

