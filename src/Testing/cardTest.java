import static org.junit.Assert.*;
import cardGame.card;



public class cardTest {

    @org.junit.Test
    public void getCardValue() {
       
        assertEquals(0, card.getValue());
    }
}