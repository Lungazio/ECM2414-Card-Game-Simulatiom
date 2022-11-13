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
public class cardDeck implements Runnable{
    private int playerId;
    private int deckId;
    public ArrayList<Integer> deck = new ArrayList<Integer>();

    // default constructor for cardDeck
    public cardDeck(int i) {
        this.playerId = playerId;
    }

    /**
     * Card is discarded to the bottom
     *
     */
    public void discardToDeck (int cardValue){
        cardDeck.this.deck.add(cardValue);
    }

    public void setDeck (ArrayList<Integer> setDeck){
        deck = setDeck;
    }
    /**
     * Removes the top most card
     * Returns value of the card
     */
    public int drawFromDeck (){
        int value = cardDeck.this.deck.get(0);
        cardDeck.this.deck.remove(0);
        return value;
    }


    public ArrayList<Integer> getCardDeck () {
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

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

    }

}
