import org.example.bank.*;
import org.example.bank.exceptions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testmethoden für die PrivateBank")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PrivateBankTest {
    static PrivateBank pb;

    @DisplayName("init")
    @BeforeEach
    public void init() throws AccountAlreadyExistsException, IOException, TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException {
        pb = new PrivateBank("JUnit 5",  0.0, 0.12);
        File dir = new File(pb.getDirectoryName());
        for (File file: dir.listFiles()) {
            if (!file.isDirectory()){
                file.delete();
            }
        }
        pb.createAccount("Hans");
        pb.createAccount("Tim");
        pb.addTransaction("Hans", new Payment("19.01.2011",-789.0, "Payment",  0.9, 0.25));
        pb.addTransaction("Tim", new Payment("19.01.2011",-789.0, "Payment",  0.9, 0.25));
        pb.addTransaction("Tim", new IncomingTransfer("03.03.2000", 80, "IncomingTransfer von Adam zu Tim, 80",  "Adam", "Tim"));
    }

    @DisplayName("PrivateBank Konstruktor")
    @Test
    @Order(0)
    public void konstruktorTest() {
        assertAll(
                () -> assertEquals("JUnit 5", pb.getName()),
                () -> assertEquals(0, pb.getIncomingInterest()),
                () -> assertEquals(0.12, pb.getOutgoingInterest()));
    }

    @DisplayName("PrivateBank Copy Konstruktor")
    @Test
    @Order(1)
    public void copyKonstruktorTest() {
        PrivateBank pbCopy = new PrivateBank(pb);
        assertAll(
                () -> assertEquals(pb.getName(), pbCopy.getName()),
                () -> assertEquals(pb.getDirectoryName(), pbCopy.getDirectoryName()),
                () -> assertEquals(pb.getIncomingInterest(), pbCopy.getIncomingInterest()),
                () -> assertEquals(pb.getOutgoingInterest(), pbCopy.getOutgoingInterest()));
        assertEquals(pb, pbCopy);
    }

    @DisplayName("Duplicate createAccount(String) Test - Exception")
    @ParameterizedTest
    @ValueSource(strings = {"Hans", "Tim"})
    @Order(2)
    public void createDuplicateAccountTest(String account) {
        assertThrows(AccountAlreadyExistsException.class, () -> pb.createAccount(account));
    }

    @DisplayName("createAccount(String) Test")
    @ParameterizedTest
    @ValueSource(strings = {"Dinesh", "Bob"})
    @Order(3)
    public void createValidAccountTest(String account) {
        assertDoesNotThrow(() -> pb.createAccount(account));
    }

    @DisplayName("createAccount(String,List) Test")
    @ParameterizedTest
    @ValueSource(strings = {"Klaus", "Harsh"})
    @Order(4)
    public void createValidAccountWithTransactionsListTest(String account) {
        assertDoesNotThrow(() -> pb.createAccount(account, List.of(
                new Payment("23.09.1897",-2500.0, "Payment numero dos",  0.8, 0.5),
                new OutgoingTransfer("30.07.2020",1890, "OutgoingTransfer to Hans",  account, "Hans"))));
    }

    @DisplayName("Duplicate createAccount(String,List) Test - AccountAlreadyExistsException")
    @ParameterizedTest
    @ValueSource(strings = {"Hans","Tim"})
    @Order(5)
    public void createInvalidAccountWithTransactionsListTest(String account){
        assertThrows(AccountAlreadyExistsException.class,
                () -> pb.createAccount(account, List.of(new Payment("23.09.1897",-2500.0, "Payment numero dos",  0.8, 0.5))));
    }

    @DisplayName("addTransaction(String, Transaction) Test")
    @ParameterizedTest
    @ValueSource(strings = {"Hans", "Tim"})
    @Order(6)
    public void addValidTransactionValidAccountTest(String account) {

        assertDoesNotThrow(() -> pb.addTransaction(account,
                new IncomingTransfer("30.07.2020",1890, "OutgoingTransfer to Hans",  "Tom", account)));
    }


    @DisplayName("Duplicate addTransaction(String, Transaction) Test - Exception")
    @ParameterizedTest
    @ValueSource(strings = {"Hans", "Tim"})
    @Order(7)
    public void addDuplicateTransactionTest(String account) {
        assertThrows(TransactionAlreadyExistException.class, () -> pb.addTransaction(account,
                new Payment("19.01.2011",-789.0, "Payment",  0.0, 0.12)));
    }


    @DisplayName("addTransaction(String, Transaction) Test - Falsches Konto")
    @ParameterizedTest
    @ValueSource(strings = {"Gina", "Bona", "Yang"})
    @Order(8)
    public void addTransactionInvalidAccountTest(String account) {
        assertThrows(AccountDoesNotExistException.class,
                () -> pb.addTransaction(account, new Payment("19.01.2011",-789.0, "Payment",  0.9, 0.25))
        );
    }

    @DisplayName("removeTransaction(String) Test")
    @ParameterizedTest
    @ValueSource(strings = {"Hans", "Tim"})
    @Order(9)
    public void removeValidTransactionTest(String account) {
        assertDoesNotThrow(() -> pb.removeTransaction(account,
                new Payment("19.01.2011",-789.0, "Payment",  0.0, 0.12)));
    }

    @DisplayName("removeTransaction(String) Test - Transaktion existiert nicht")
    @ParameterizedTest
    @ValueSource(strings = {"Hans", "Tim"})
    @Order(10)
    public void removeInvalidTransactionTest(String account) {
        assertThrows(TransactionDoesNotExistException.class,
                () -> pb.removeTransaction(account, new Payment("19.01.2011",-789.0, "Payment",  0.9, 0.25))
        );
    }

    @DisplayName("containsTransaction(String, Transaction) Test")
    @ParameterizedTest
    @ValueSource(strings = {"Hans", "Tim"})
    @Order(11)
    public void containsTransactionTrueTest(String account) {
        assertTrue(pb.containsTransaction(account,
                new Payment("19.01.2011",-789.0, "Payment",  0.0, 0.12)));
    }

    @DisplayName("containsTransaction(String, Transaction) Test - Transaktion existiert nicht")
    @ParameterizedTest
    @ValueSource(strings = {"Hans", "Tim"})
    @Order(12)
    public void containsTransactionFalseTest(String account) {
        assertFalse(pb.containsTransaction(account, new OutgoingTransfer("30.07.2020",1890, "OutgoingTransfer to Hans",  account, "Hans")));
    }

    @DisplayName("getAccountBalance(String) Test")
    @ParameterizedTest
    @CsvSource({"Hans, -883.68", "Tim, -803.68"})
    @Order(13)
    public void getAccountBalanceTest(String account, double balance) {
        assertEquals(balance, pb.getAccountBalance(account));
    }

    @DisplayName("getTransactions(String) Test")
    @Test
    @Order(14)
    public void getTransactionTest() {
        List<Transaction> transactionList = List.of(
                new Payment("19.01.2011",-789.0, "Payment",  0.0, 0.12),
                new IncomingTransfer("03.03.2000", 80, "IncomingTransfer von Adam zu Tim, 80",  "Adam", "Tim"));
        assertEquals(transactionList, pb.getTransactions("Tim"));
    }

    @DisplayName("getTransactionsByType(String, Boolean) Test")
    @Test
    @Order(15)
    public void getTransactionsByTypeTest() {
        List<Transaction> transactionListPositive = List.of(
                new IncomingTransfer("03.03.2000", 80, "IncomingTransfer von Adam zu Tim, 80",  "Adam", "Tim"));
        assertEquals(transactionListPositive, pb.getTransactionsByType("Tim", true));

        List<Transaction> transactionListNegative = List.of(
                new Payment("19.01.2011",-789.0, "Payment",  0.0, 0.12));
        assertEquals(transactionListNegative, pb.getTransactionsByType("Tim", false));
    }

    @DisplayName("getTransactionsSorted(String, Boolean) Test")
    @Test
    @Order(16)
    public void getTransactionsSortedTest() {
        List<Transaction> transactionListAsc = List.of(
                new Payment("19.01.2011",-789.0, "Payment",  0.0, 0.12),
                new IncomingTransfer("03.03.2000", 80, "IncomingTransfer von Adam zu Tim, 80",  "Adam", "Tim"));
        assertEquals(transactionListAsc ,pb.getTransactionsSorted("Tim", true));

        List<Transaction> transactionListDesc = List.of(
                new IncomingTransfer("03.03.2000", 80, "IncomingTransfer von Adam zu Tim, 80",  "Adam", "Tim"),
                new Payment("19.01.2011",-789.0, "Payment",  0.0, 0.12));
        assertEquals(transactionListDesc ,pb.getTransactionsSorted("Tim", false));
    }


    @DisplayName("readAccounts() Test")
    @Test
    @Order(17)
    public void readAccountsTest() throws IOException {
        PrivateBank pb1 = new PrivateBank("JUnit 5",  0.0, 0.12);
        pb1.readAccounts();
        assertEquals(pb1.accountsToTransactions, pb.accountsToTransactions);
    }
}
