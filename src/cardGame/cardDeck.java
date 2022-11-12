package cardGame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
    public ArrayList<Integer> deck = new ArrayList<Integer>();

    // initialize deck
    public void createDeck(int playerId) {
        this.playerId = playerId;
        this.deckId = playerId;
    }

    class Deck implements Runnable{

        public void dispose(int deckId) {
            // dispose to the bottom of
            // deckId + 1 or if last player,
            // dispose to id of first player
        }


        /**
         * Card is discarded to the bottom
         *
         */
        public void discardedToDeck (int cardValue){
            cardDeck.this.deck.add(cardValue);
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

    public ArrayList<Integer> getCardDeck (int deckId) {
        return this.deck;
    }

}
