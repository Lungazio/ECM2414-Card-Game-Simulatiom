package cardGame;

import java.io.*;
import java.util.ArrayList;

/**
 * cardDeck class:
 * Holds all data related to card decks,

 * Includes get/set methods for
 * draw and discard decks
 *
 * @author Daphne Yap & Julian Lung
 * @version 5.0
 *
 */
public class cardDeck {
    private int playerId;
    public ArrayList<card> deck = new ArrayList<>();

    // default constructor for cardDeck
    public cardDeck(int playerId) {
        this.playerId = playerId;
    }

    // returns the ID of current deck
    public int getDeckId (){
        return playerId;
    }

    /**
     * Adds a card into this deck,
     * (player discards a card)
     *
     * Takes a card, adds it to current deck
     *
     *
     * @param c : the card to be added in the deck,
     *            discarded from the player to the left
     *
     * Used in every playerTurn
     */
    public synchronized void discard (card c){
        // add as the last element
        // (bottom of the deck equivalent)
        deck.add(c);
    }

    /**
     * Draws and returns a card from the current deck,
     * and remove that element
     *
     * @return the card on the top-most of the deck (first element)
     *
     * Used in every playerTurn (draw)
     */
    public synchronized card drawFromDeck (){
        // get and store the first element of the ArrayList
        // (top of the deck equivalent)
        card cardDraw = deck.get(0);

        // remove said element and return cardDraw
        cardDeck.this.deck.remove(0);
        return cardDraw;
    }

    /**
     * Outputs the contents of current deck
     * to a readable string
     *
     * @return formatted string containing
     * deck contents
     *
     * Used to print deck output file
     */
    public String printDeck() {
        String output = "player" + playerId + " deck: " + deck.get(0).getValue();

        // loop to concatenate and format contents
        if (deck.size() > 1){
            for (int i = 1; i < deck.size(); i++) {
                output = output + " , " + deck.get(i).getValue();
            }
        }
        return (output);
    }

    /**
     * Adds a card to the deck
     *
     * @param card integer value from input pack
     * Used when distributing cards to decks
     */
    public void addToDeck(card card) {
        deck.add(card);
    }

    public ArrayList<card> getDeck(){
        return deck;
    }

    public String create_deck_log (){
        String filename = "Deck" + playerId + "_output.txt";
        File log_file = new File(filename);

        try {
            if (log_file.exists())
                log_file.delete();
            log_file.createNewFile();
            return filename;

            //throw an exception if failed to make log
        } catch (IOException e) {
            System.out.println("Couldn't create deck output file: " + filename);
            return null;
        }
    }

    public void write_deck_log (String text){
        Writer output = null;
        try {
            output = new BufferedWriter(new FileWriter("Deck" + playerId + "_output.txt", true));
            output.append(text).append("\n");
            output.close();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    public void setDeck (ArrayList<card> deckCards){
        deck = deckCards;
    }
}
