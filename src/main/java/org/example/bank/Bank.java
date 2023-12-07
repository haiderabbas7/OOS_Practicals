package org.example.bank;

import org.example.bank.exceptions.*;

import java.io.IOException;
import java.util.List;

/**
 * Interface for a generic bank. Provides multiple methods to handle the interaction between
 * accounts and transactions.
 */
public interface Bank {

    /**
     * Fügt ein Konto zur Bank hinzu. Speziell wird in der Map ein neuer String-Key eingetragen.
     * @param account Der Name des zu hinzufügenden Accounts
     * @throws AccountAlreadyExistsException Falls der Account schon existiert
     */
    void createAccount(String account) throws AccountAlreadyExistsException, IOException;

    /**
     * Fügt ein Konto zur Bank hinzu. Speziell wird in der Map ein neuer String-Key eingetragen.
     * Anders als die andere createAccount(String) Methode gibt man hier dem Konto schon eine Liste an Transaktionen vor.
     *
     * @param account Der Name des zu hinzufügenden Accounts
     * @param transactions Eine Liste an Transaktionen, welche dem Konto zugeschrieben werden sollen
     * @throws AccountAlreadyExistsException
     * @throws TransactionAlreadyExistException if the transaction already exists
     * @throws TransactionAttributeException    if the validation check for certain attributes fail
     */
    void createAccount(String account, List<Transaction> transactions)
            throws AccountAlreadyExistsException, TransactionAlreadyExistException, TransactionAttributeException, IOException;

    /**
     * Adds a transaction to an already existing account.
     *
     * @param account     the account to which the transaction is added
     * @param transaction the transaction which should be added to the specified account
     * @throws TransactionAlreadyExistException if the transaction already exists
     * @throws AccountDoesNotExistException     if the specified account does not exist
     * @throws TransactionAttributeException    if the validation check for certain attributes fail
     */
    void addTransaction(String account, Transaction transaction)
            throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException, IOException;

    /**
     * Removes a transaction from an account. If the transaction does not exist, an exception is
     * thrown.
     *
     * @param account     the account from which the transaction is removed
     * @param transaction the transaction which is removed from the specified account
     * @throws AccountDoesNotExistException     if the specified account does not exist
     * @throws TransactionDoesNotExistException if the transaction cannot be found
     */
    void removeTransaction(String account, Transaction transaction)
            throws AccountDoesNotExistException, TransactionDoesNotExistException, IOException;

    /**
     * Checks whether the specified transaction for a given account exists.
     *
     * @param account     the account from which the transaction is checked
     * @param transaction the transaction to search/look for
     */
    boolean containsTransaction(String account, Transaction transaction) throws AccountDoesNotExistException;

    /**
     * Calculates and returns the current account balance.
     *
     * @param account the selected account
     * @return the current account balance
     */
    double getAccountBalance(String account) throws AccountDoesNotExistException;

    /**
     * Returns a list of transactions for an account.
     *
     * @param account the selected account
     * @return the list of all transactions for the specified account
     */
    List<Transaction> getTransactions(String account) throws AccountDoesNotExistException;

    /**
     * Returns a sorted list (-> calculated amounts) of transactions for a specific account. Sorts the list either in ascending or descending order
     * (or empty).
     *
     * @param account the selected account
     * @param asc     selects if the transaction list is sorted in ascending or descending order
     * @return the sorted list of all transactions for the specified account
     */
    List<Transaction> getTransactionsSorted(String account, boolean asc) throws AccountDoesNotExistException;

    /**
     * Returns a list of either positive or negative transactions (-> calculated amounts).
     *
     * @param account  the selected account
     * @param positive selects if positive or negative transactions are listed
     * @return the list of all transactions by type
     */
    List<Transaction> getTransactionsByType(String account, boolean positive) throws AccountDoesNotExistException;
}
