package Testing;

import static org.junit.Assert.*;

import org.junit.Test;
import cardGame.player;
import cardGame.card;
import cardGame.cardDeck;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


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
        } catch (Exception ex) {
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
        try {
            deck.drawFromDeck();
        } catch (Exception ex) {
            pass = false;
        }
        assertTrue(pass);
    }


    @org.junit.Test
    public void addToDeckTest() {
        cardDeck deck = new cardDeck(1);
        card testCard = new card(1);
        boolean pass = true;
        try {
            deck.addToDeck(testCard);
        } catch (Exception ex) {
            pass = false;
        }
        assertTrue(pass);
    }

    @org.junit.Test
    public void setDeckTest() {
        cardDeck deck = new cardDeck(1);
        ArrayList<card> testDeck = new ArrayList<>(Arrays.asList(new card(4), new card(3), new card(2), new card(4)));
        deck.setDeck(testDeck);
        assert (deck.getDeck().get(0).getValue() == 4);
        assert (deck.getDeck().get(1).getValue() == 3);
        assert (deck.getDeck().get(2).getValue() == 2);
        assert (deck.getDeck().get(3).getValue() == 4);
    }

    @org.junit.Test
    public void create_deck_log() {
        cardDeck deck = new cardDeck(1);
        ArrayList<card> testDeck = new ArrayList<>(Arrays.asList(new card(4), new card(3), new card(2), new card(4)));
        deck.setDeck(testDeck);
        File file = new File(deck.create_deck_log());

        assert (file.isFile());
    }

    @org.junit.Test
    public void write_deck_log() {
        cardDeck deck = new cardDeck(1);
        ArrayList<card> testDeck = new ArrayList<>(Arrays.asList(new card(4), new card(3), new card(2), new card(4)));
        deck.setDeck(testDeck);
        String fileName = deck.create_deck_log();
        deck.write_deck_log(deck.printDeck());
        String deckPrint = deck.printDeck();
        assertEquals(fileReader(fileName), deckPrint);
    }


    public String fileReader(String fileName) {
        File file = new File(fileName);
        String absolutePath = file.getAbsolutePath();

        String line = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(absolutePath))) {
            line = reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(line);
        return line;
    }

    @org.junit.Test
    public void run() {

    }
}