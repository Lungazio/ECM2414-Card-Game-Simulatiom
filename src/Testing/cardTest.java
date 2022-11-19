import static org.junit.Assert.*;
import cardGame.card;



public class cardTest {

    @org.junit.Test
    public void getCardValue() {
        card card = new card(1);
        assertEquals(1, card.getValue());
    }
}