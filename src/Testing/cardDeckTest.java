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
         card testCard = new card(1);
         boolean pass = true;
         try {
             discardDeck.discard(testCard);
         }catch (Exception ex){
             pass = false;
         }
         assertTrue(pass);
     }

     @org.junit.Test
     public void drawFromDeckTest() {
         cardDeck deck = new cardDeck(1);
         card testCard = new card(1);
         deck.addToDeck(testCard);
         boolean pass = true;
         try{
             deck.drawFromDeck();
         }
         catch (Exception ex){
             pass = false;
         }
         assertTrue(pass);
     }


     @org.junit.Test
     public void addToDeckTest() {
         cardDeck deck = new cardDeck(1);
         card testCard = new card(1);
         boolean pass = true;
         try{
             deck.addToDeck(testCard);
         }
         catch (Exception ex){
             pass = false;
         }
         assertTrue(pass);
     }

     @org.junit.Test
     public void run(){

     }
 }