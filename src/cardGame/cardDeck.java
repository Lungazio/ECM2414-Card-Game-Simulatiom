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
    public cardDeck(int playerId) {
        this.playerId = playerId;
    }

    /**
     * Card is discarded to the bottom
     *
     */

    public int getDeckId (){
        return playerId;
    }

    public synchronized void discard (card cardValue){
        deck.add(cardValue);
    }

    public void setDeck (ArrayList<card> setDeck){
        deck = setDeck;
    }
    /**
     * Removes the top most card
     * Returns value of the card
     */
    public synchronized card drawFromDeck (){
        card cardTemp = deck.get(0);
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

    public void printDeck() {
        String output = "decks of player" + playerId + " : " + deck.get(0).getValue();
        if (deck.size() > 1){
            for (int i = 1; i < deck.size(); i++) {
                output = output + " , " + deck.get(i).getValue();
            }
        }
        System.out.println(output);
    }

    public void addToDeck(int cardValue) {
        deck.add(new card(cardValue));
    }
}
