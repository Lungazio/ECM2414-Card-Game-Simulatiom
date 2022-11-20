 import static org.junit.Assert.*;
 import org.junit.Test;
 import cardGame.player;
 import cardGame.card;
 import cardGame.cardDeck;

 import java.util.ArrayList;


 public class cardDeckTest {

     @org.junit.Test
     public void getDeckIdTest() {
         cardDeck deck = new cardDeck(1);
         assertEquals(1, deck.getDeckId());
     }
     @org.junit.Test
     public void discardTest() {
         cardDeck discardDeck = new cardDeck(1);
         card testCard;
         boolean pass = true;
         testCard = new card(1);
         try {
             discardDeck.discard(testCard);
         }catch (Exception ex){
             pass = false;
         }
         assertTrue(pass);
     }

     @org.junit.Test
     public void drawFromDeckTest() {
     }


     @org.junit.Test
     public void addToDeckTest() {
     }
 }