import static org.junit.Assert.*;
import cardGame.card;



public class cardTest {

    @org.junit.Test
    public void createCardTest() {
        boolean pass = true;
        try{
            card Testcard = new card(1);
        }
        catch (Exception ex){
            pass = false;
        }
        assertTrue(pass);
    }


    @org.junit.Test
    public void getCardValueTest() {
        card card = new card(1);
        assertEquals(1, card.getValue());
    }
}