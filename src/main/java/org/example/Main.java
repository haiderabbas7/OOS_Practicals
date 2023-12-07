package org.example;

import org.example.bank.*;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        /*PrivateBank pb = new PrivateBank("pb", 0.5, 0.5);
        PrivateBank pbCopy = new PrivateBank(pb);
        PrivateBankAlt pbAlt = new PrivateBankAlt("pbAlt", 0.5, 0.5);
        Transfer t1 = new Transfer("t1", 1000, "Gehalt", "Karl", "Heinz");
        Transfer t2 = new Transfer("t2", 300, "Urlaub", "Heinz", "Karl");
        OutgoingTransfer ot1 = new OutgoingTransfer("ot1", 1000, "Gehalt", "Karl", "Heinz");
        OutgoingTransfer ot2 = new OutgoingTransfer("ot2", 300, "Urlaub", "Heinz", "Karl");
        IncomingTransfer it1 = new IncomingTransfer("it1", 1000, "Gehalt", "Karl", "Heinz");
        IncomingTransfer it2 = new IncomingTransfer("it2", 300, "Urlaub", "Heinz", "Karl");
        Payment p1 = new Payment("p1", 1000, "Einzahlung");
        Payment p2 = new Payment("p2", -1000, "Auszahlung");
        System.out.println("XXXXXXXXXXXX--Test: equals()--XXXXXXXXXXXX\n" + pb.equals(pbCopy));
        System.out.println("\nXXXXXXXXXXXX--Test: Erstellen von Kontos mit createAccout(String) und createAccount(String,List)--XXXXXXXXXXXX");

        pb.createAccount("Heinz");
        pb.createAccount("Peter");
        pbAlt.createAccount("Karl");
        pbAlt.createAccount("Heinz");
        try{
            pb.createAccount("Heinz");
        }
        catch (AccountAlreadyExistsException ex){
            System.out.println("Hier wird eine Exception ausgegeben, da der Account Heinz unter der Bank pb schon existiert.");
        }
        System.out.println(pb.toString());
        List<Transaction> l = new ArrayList<>();
        l.add(t1);
        l.add(t2);
        l.add(p1);
        pb.createAccount("Karl", l);
        l.add(t1);
        try{
            pb.createAccount("Hans", l);
        }
        catch (TransactionAlreadyExistException ex){
            System.out.println("Hier wird eine Exception ausgegeben, da es die Transaktion t1 zweimal gibt in der Liste l.");
        }
        System.out.println(pb.toString());


        System.out.println("\nXXXXXXXXXXXX--Test: Hinzufügen von Transaktionen mit addTransaction(String,Transaction)--XXXXXXXXXXXX");
        pb.addTransaction("Peter",t1);
        pb.addTransaction("Peter",t2);
        pb.addTransaction("Peter",p1);
        System.out.println(pb.toString());
        try{
            pb.addTransaction("Peter",t1);
        }
        catch (TransactionAlreadyExistException ex){
            System.out.println("Hier wird eine Exception ausgegeben, da es die Transaktion t1 schon mal gibt unter dem Konto 'Peter'.");
        }
        System.out.println(pb.toString());


        System.out.println("\nXXXXXXXXXXXX--Test: Entfernen von Transaktionen mit removeTransaction(String,Transaction)--XXXXXXXXXXXX");
        pb.removeTransaction("Hans", t1);
        System.out.println(pb.toString());

        System.out.println("\nXXXXXXXXXXXX--Test: Suchen von Transaktionen mit containsTransaction(String,Transaction)--XXXXXXXXXXXX");
        System.out.println("\n" + pb.containsTransaction("Karl", t1));

        System.out.println("\nXXXXXXXXXXXX--Test: Account Balance mit den zwei Varianten--XXXXXXXXXXXX");
        pbAlt.addTransaction("Karl", t1);
        pbAlt.addTransaction("Karl", t2);
        pbAlt.addTransaction("Karl", p1);
        pb.createAccount("Thomas");
        pb.addTransaction("Thomas", ot1);
        pb.addTransaction("Thomas", ot2);
        pb.addTransaction("Thomas", p1);
        System.out.println(pb.toString());
        System.out.println(pbAlt.toString());
        System.out.println("\n" + pb.getAccountBalance("Thomas") + "\n" + pbAlt.getAccountBalance("Karl"));

        System.out.println("\nXXXXXXXXXXXX--Test: getTransactions, getTransactionsSorted und getTransactionsByType--XXXXXXXXXXXX");
        pb.createAccount("Gustav");
        Payment pA = new Payment("pA", -3, "Einzahlung");
        Payment pB = new Payment("pB", -2, "Einzahlung");
        Payment pC = new Payment("pC", -1, "Einzahlung");
        Payment pD = new Payment("pD", 1, "Einzahlung");
        Payment pE = new Payment("pE", 2, "Einzahlung");
        Payment pF = new Payment("pF", 3, "Einzahlung");
        Payment pG = new Payment("pG", 4, "Einzahlung");
        Payment pH = new Payment("pH", 5, "Einzahlung");
        pb.addTransaction("Gustav", pA);
        pb.addTransaction("Gustav", pB);
        pb.addTransaction("Gustav", pC);
        pb.addTransaction("Gustav", pD);
        pb.addTransaction("Gustav", pE);
        pb.addTransaction("Gustav", pF);
        pb.addTransaction("Gustav", pG);
        pb.addTransaction("Gustav", pH);
        System.out.println("getTransactions: " + pb.getTransactions("Gustav").toString());
        System.out.println("getTransactionsSorted, aufsteigend: " + pb.getTransactionsSorted("Gustav", true).toString());
        System.out.println("getTransactionsSorted, absteigend: " + pb.getTransactionsSorted("Gustav", false).toString());
        System.out.println("getTransactionsByType, positiv: " + pb.getTransactionsByType("Gustav", true).toString());
        System.out.println("getTransactionsByType, negativ: " + pb.getTransactionsByType("Gustav", false).toString());*/

        PrivateBank pb = new PrivateBank("pb", 0.5, 0.5);
        System.out.println(pb.toString() + "\n\n\n\n\n\n\n\n\n\n\n");
        OutgoingTransfer ot1 = new OutgoingTransfer("ot1", 1000, "Gehalt", "Karl", "Heinz");
        OutgoingTransfer ot2 = new OutgoingTransfer("ot2", 300, "Urlaub", "Heinz", "Karl");
        IncomingTransfer it1 = new IncomingTransfer("it1", 1000, "Gehalt", "Karl", "Heinz");
        IncomingTransfer it2 = new IncomingTransfer("it2", 300, "Urlaub", "Heinz", "Karl");
        Payment p1 = new Payment("p1", 1000, "Einzahlung");
        Payment p2 = new Payment("p2", -1000, "Auszahlung");
        pb.createAccount("Heinz");
        pb.addTransaction("Heinz", ot1);
        pb.addTransaction("Heinz", ot2);
        pb.addTransaction("Heinz", it1);
        pb.addTransaction("Heinz", it2);
        pb.addTransaction("Heinz", p1);
        pb.addTransaction("Heinz", p2);
        System.out.println(pb.toString());
    }
}