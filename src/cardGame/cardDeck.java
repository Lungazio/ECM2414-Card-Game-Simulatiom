package cardGame;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * cardDeck class:
 * Holds all data related to card decks,
 *
 * "each player picks a card from the top
 * of the deck to their left, discards one
 * to the bottom of the deck to their right
 *
 * Example scenarios:
 * player 1 picks from deck 1,
 * player 1 disposes to deck 2
 *
 * player 4 picks from deck 4
 * player 4 disposes to deck 1
 *
 * Includes get/set methods
 * Holds data of decks
 *
 * @author Daphne Yap & Julian Lung
 * @version 1.0
 *
 */
public class cardDeck {
    private int playerId;
    private int deckId;
    public ArrayList<card> deck = new ArrayList<>();

    // default constructor for cardDeck
    public cardDeck(int i) {
        this.playerId = playerId;
    }

    /**
     * Card is discarded to the bottom
     *
     */
    public void discard (card cardValue){
        cardDeck.this.deck.add(cardValue);
    }

    public void setDeck (ArrayList<card> setDeck){
        deck = setDeck;
    }
    /**
     * Removes the top most card
     * Returns value of the card
     */
    public card drawFromDeck (){
        card cardTemp = cardDeck.this.deck.get(0);
        cardDeck.this.deck.remove(0);
        return cardTemp;
    }


    public ArrayList<card> getCardDeck () {
        return this.deck;
    }

    public void generateDeckOutput(int deckId){
        String text = "deck " + deckId + " contents: " + this.deck.toString();
        System.out.println(text);
        Writer output = null;
        try {
            output = new BufferedWriter(new FileWriter("deck" + deckId + "_output.txt", true));
            output.append(text).append("\n");
            output.close();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    public void addToDeck(int cardValue) {
        deck.add(new card(cardValue));
    }
}
