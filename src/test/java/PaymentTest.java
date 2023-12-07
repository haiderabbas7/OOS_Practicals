import org.example.bank.Payment;

import org.example.bank.exceptions.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class PaymentTest {
    static Payment payment1;
    static Payment payment2;
    static Payment copyPayment;

    @BeforeEach
    public void init() {
        payment1 = new Payment( "12.03.2008",321.0, "Payment numero uno" );
        payment2 = new Payment("23.09.1897",-2500.0, "Payment numero dos",  0.8, 0.5);
        copyPayment = new Payment(payment2);
    }


    @Test
    public void ersterKonstruktorTest() {
        assertEquals("12.03.2008", payment1.getDate());
        assertEquals("Payment numero uno", payment1.getDescription());
        assertEquals(321, payment1.getAmount());
    }

    @Test
    public void zweiterKonstruktorTest() {
        assertEquals("23.09.1897", payment2.getDate());
        assertEquals("Payment numero dos", payment2.getDescription());
        assertEquals(-2500, payment2.getAmount());
        assertEquals(0.5, payment2.getOutgoingInterest());
        assertEquals(0.8, payment2.getIncomingInterest());
    }

    @Test
    public void copyKonstruktorTest() {
        assertEquals(payment2.getDate(), copyPayment.getDate());
        assertEquals(payment2.getDescription(), copyPayment.getDescription());
        assertEquals(payment2.getAmount(), copyPayment.getAmount());
        assertEquals(payment2.getIncomingInterest(), copyPayment.getIncomingInterest());
        assertEquals(payment2.getOutgoingInterest(), copyPayment.getOutgoingInterest());
    }


    @Test
    public void incomingInterestTest() {
        double expected = payment1.getAmount() - payment1.getIncomingInterest() * payment1.getAmount();
        assertTrue(payment1.getAmount() >= 0);
        assertEquals(expected, payment1.calculate());
    }

    @Test
    public void outgoingInterestTest() {
        double expected = payment2.getAmount() + payment2.getOutgoingInterest() * payment2.getAmount();
        assertTrue(payment2.getAmount() < 0);
        assertEquals(expected, payment2.calculate());
    }

    @Test
    public void equalsTrueTest() {
        assertEquals(payment2, copyPayment);
    }

    @Test
    public void equalsFalseTest() {
        assertNotEquals(payment1, payment2);
    }

    @Test
    public void toStringTest() {
        String string = "23.09.1897, -3750.0, Payment numero dos, 0.8, 0.5";
        assertEquals(string, payment2.toString());
    }
    @Test
    public void setOutgoingInterestTest(){
        assertThrows(PaymentInvalidPercentageException.class,
                () -> payment2.setOutgoingInterest(20.0)
        );
    }
    @Test
    public void setIncomingInterestTest(){
        assertThrows(PaymentInvalidPercentageException.class,
                () -> payment2.setIncomingInterest(20.0)
        );
    }
}
