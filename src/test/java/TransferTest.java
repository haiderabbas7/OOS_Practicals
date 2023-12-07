import org.example.bank.Transfer;
import org.example.bank.OutgoingTransfer;
import org.example.bank.IncomingTransfer;

import org.example.bank.exceptions.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class TransferTest {

    static Transfer incomingTransfer;
    static Transfer outgoingTransfer;
    static Transfer copyOutgoingTransfer;
    static Transfer copyIncomingTransfer;

    @BeforeEach
    public void init() {
        incomingTransfer = new IncomingTransfer("03.03.2000", 80, "IncomingTransfer von Peter an Dieter, 80");
        outgoingTransfer = new OutgoingTransfer("30.07.2020", 1890, "OutgoingTransfer an Hans", "Dieter", "Hans");
        copyOutgoingTransfer = new OutgoingTransfer(outgoingTransfer);
        copyIncomingTransfer = new IncomingTransfer(incomingTransfer);
    }


    @Test
    public void ersterKonstruktorTest() {
        assertEquals("03.03.2000", incomingTransfer.getDate());
        assertEquals("IncomingTransfer von Peter an Dieter, 80", incomingTransfer.getDescription());
        assertEquals(80, incomingTransfer.getAmount());

    }

    @Test
    public void zweiterKonstruktorTest() {
        assertEquals("30.07.2020", outgoingTransfer.getDate());
        assertEquals("OutgoingTransfer an Hans", outgoingTransfer.getDescription());
        assertEquals(1890, outgoingTransfer.getAmount());
        assertEquals("Dieter", outgoingTransfer.getSender());
        assertEquals("Hans", outgoingTransfer.getRecipient());
    }

    @Test
    public void copyKonstruktorOutgoingTransferTest() {
        assertEquals(outgoingTransfer.getAmount(), copyOutgoingTransfer.getAmount());
        assertEquals(outgoingTransfer.getDate(), copyOutgoingTransfer.getDate());
        assertEquals(outgoingTransfer.getRecipient(), copyOutgoingTransfer.getRecipient());
        assertEquals(outgoingTransfer.getAmount(), copyOutgoingTransfer.getAmount());
        assertEquals(outgoingTransfer.getSender(), copyOutgoingTransfer.getSender());
        assertEquals(outgoingTransfer.getDescription(), copyOutgoingTransfer.getDescription());
    }

    @Test
    public void copyKonstruktorIncomingTransferTest(){
        assertEquals(incomingTransfer.getAmount(), copyIncomingTransfer.getAmount());
        assertEquals(incomingTransfer.getDate(), copyIncomingTransfer.getDate());
        assertEquals(incomingTransfer.getRecipient(), copyIncomingTransfer.getRecipient());
        assertEquals(incomingTransfer.getAmount(), copyIncomingTransfer.getAmount());
        assertEquals(incomingTransfer.getSender(), copyIncomingTransfer.getSender());
        assertEquals(incomingTransfer.getDescription(), copyIncomingTransfer.getDescription());
    }

    @Test
    public void calculateIncomingTransferTest() {
        assertInstanceOf(IncomingTransfer.class, incomingTransfer);
        assertEquals(incomingTransfer.getAmount(), incomingTransfer.calculate());
    }

    @Test
    public void calculateOutgoingTransferTest() {
        assertInstanceOf(OutgoingTransfer.class, outgoingTransfer);
        assertEquals(-outgoingTransfer.getAmount(), outgoingTransfer.calculate());
    }

    @Test
    public void equalsTrueTest() {
        assertEquals(outgoingTransfer, copyOutgoingTransfer);
    }

    @Test
    public void equalsFalseTest() {
        assertNotEquals(incomingTransfer, outgoingTransfer);
    }

    @Test
    public void toStringTest() {
        assertEquals("30.07.2020, -1890.0, OutgoingTransfer an Hans, Dieter, Hans", outgoingTransfer.toString());
    }
    @Test
    public void setAmountTest(){
        assertThrows(TransferNegativeAmountException.class,
                () -> incomingTransfer.setAmount(-100.0)
        );
    }
}
