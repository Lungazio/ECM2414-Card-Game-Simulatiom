package Testing;
import static org.junit.Assert.*;
import cardGame.player;
import cardGame.cardDeck;
import cardGame.card;
import org.junit.After;
import org.junit.Before;

import java.io.*;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;


public class playerTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private static int winner;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @org.junit.Test
    public void getPlayer() {
        cardDeck sampleDeck1 = new cardDeck(1);
        cardDeck sampleDeck2 = new cardDeck(2);
        player testPlayer = new player(1, sampleDeck1, sampleDeck2);
        assertEquals(1, testPlayer.getPlayerId());
    }

    @org.junit.Test
    public void getHand() {
        cardDeck testDeck = new cardDeck(1);
        card testCard = new card(1);
        player testPlayer = new player(1, testDeck, testDeck);
        testPlayer.addToHand(testCard);
        assertNotNull(testPlayer.getHand().get(0));
    }

    @org.junit.Test
    public void addToHand() {
        cardDeck testDeck = new cardDeck(1);
        card testCard = new card(1);
        player testPlayer = new player(1, testDeck, testDeck);
        boolean pass = true;
        try {
            testPlayer.addToHand(testCard);
        } catch (Exception ex) {
            pass = false;
        }
        assertTrue(pass);
    }

    @org.junit.Test
    public void createLogTest() {
        cardDeck sampleDeck1 = new cardDeck(1);
        cardDeck sampleDeck2 = new cardDeck(2);

        player testPlayer = new player(1, sampleDeck1, sampleDeck2);
        player testPlayer2 = new player(2, sampleDeck2, sampleDeck1);

        String fileName1 = null;
        String fileName2 = null;
        boolean pass = true;
        try {
            fileName1 = testPlayer.create_log_file();
            fileName2 = testPlayer2.create_log_file();
        } catch (Exception e) {
            e.printStackTrace();
            pass = false;
        }

        assert (pass);
        assert (fileFound(fileName1));
        assert (fileFound(fileName2));
    }

    public boolean fileFound(String fileName) {
        File file = new File(fileName);
        return (file.isFile());
    }

    @org.junit.Test
    public void writeLogTest() throws IOException {
        cardDeck sampleDeck1 = new cardDeck(1);
        cardDeck sampleDeck2 = new cardDeck(2);

        player testPlayer = new player(1, sampleDeck1, sampleDeck2);
        player testPlayer2 = new player(2, sampleDeck2, sampleDeck1);

        String string1 = "Line 1 player 1";
        String string2 = "Line 1 player 2";
        testPlayer.writeLog(string1);
        testPlayer2.writeLog(string2);

        assertEquals(fileReader("Player1_output.txt"), string1);
        assertEquals(fileReader("Player2_output.txt"), string2);
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

    public static void assertContains(String string, String subString) {
        assertTrue(string.contains(subString));
    }

    @org.junit.Test
    public void getStringHand() {
        cardDeck testDeck = new cardDeck(1);
        card testCard = new card(1);
        player testPlayer = new player(1, testDeck, testDeck);
        testPlayer.addToHand(testCard);
        assertContains("1", testPlayer.printHand());
    }

    @org.junit.Test
    public void winnerCheck() {
        cardDeck testDeck = new cardDeck(1);
        player testPlayer = new player(1, testDeck, testDeck);
        card testCard = new card(1);
        for (int i = 0; i < 4; i++) {
            testPlayer.addToHand(testCard);
        }
        assertTrue(testPlayer.winnerCheck());

    }

    @org.junit.Test
    public void printTurn() {
        boolean pass = true;
        cardDeck testDeck = new cardDeck(1);
        player testPlayer = new player(1, testDeck, testDeck);
        card testCard = new card(1);
        for (int i = 0; i < 4; i++) {
            testPlayer.addToHand(testCard);
        }
        try {
            testPlayer.printTurn(1, 1);
            assertContains(outContent.toString(), "player 1 draws a 1 from deck 1");
        } catch (AssertionError e) {
            pass = false;
        }
        assertTrue(pass);
    }

    @org.junit.Test
    public void setWinnerTest() {
        boolean pass = true;
        cardDeck testDeck = new cardDeck(1);
        player testPlayer = new player(1, testDeck, testDeck);

        try {
            testPlayer.setWinner(1);
            assertEquals(1, testPlayer.getWinnerID());
        } catch (AssertionError e) {
            pass = false;
        }
        assertTrue(pass);
    }

    @org.junit.Test
    public void getWinnerTest() {
        boolean pass = true;
        cardDeck testDeck = new cardDeck(1);
        player testPlayer = new player(1, testDeck, testDeck);
        try {
            Integer.valueOf(testPlayer.getWinnerID());
        } catch (NumberFormatException e) {
            pass = false;
        }
        assertTrue(pass);
    }

    @org.junit.Test
    public void end() {
        cardDeck testDeck = new cardDeck(1);
        player testPlayer = new player(1, testDeck, testDeck);
        boolean pass = true;
        card testCard = new card(1);
        for (int i = 0; i < 4; i++) {
            testPlayer.addToHand(testCard);
        }
        try {
            testPlayer.setWinner(1);
            testPlayer.end();
            assertContains(outContent.toString(), "player 1 wins");
        } catch (AssertionError e) {
            pass = false;
        }
        assertTrue(pass);
    }

    @org.junit.Test
    public void draw() {
        cardDeck sampleDeck1 = new cardDeck(1);
        cardDeck sampleDeck2 = new cardDeck(2);
        player testPlayer = new player(1, sampleDeck1, sampleDeck2);
        card testCard = new card(1);
        boolean pass = true;

        try {
            for (int i = 0; i < 4; i++) {
                sampleDeck1.addToDeck(testCard);
            }
            assertEquals(1, testPlayer.draw());
        } catch (AssertionError e) {
            pass = false;
        }
        assertTrue(pass);
    }

    @org.junit.Test
    public void discard() {
        cardDeck sampleDeck1 = new cardDeck(1);
        cardDeck sampleDeck2 = new cardDeck(2);
        player testPlayer = new player(1, sampleDeck1, sampleDeck2);
        card testCard = new card(1);
        card testCard2 = new card(2);
        boolean pass = true;

        try {
            for (int i = 0; i < 3; i++) {
                testPlayer.addToHand(testCard);
            }
            testPlayer.addToHand(testCard2);
            int discardTestValue = testPlayer.discard();
            assertEquals(2, discardTestValue);
        } catch (AssertionError e) {
            pass = false;
        }
        assertTrue(pass);
    }

    @org.junit.Test
    public void setHand() {
        cardDeck sampleDeck1 = new cardDeck(1);
        cardDeck sampleDeck2 = new cardDeck(2);
        player testPlayer = new player(1, sampleDeck1, sampleDeck2);

        ArrayList<card> testPlayerHand = new ArrayList<>(
                Arrays.asList(new card(1), new card(2),
                        new card(3), new card(4)));

        boolean pass = true;
        try {
            testPlayer.setHand(testPlayerHand);
        } catch (AssertionError e) {
            pass = false;
        }

        assertTrue(pass);
        assert (testPlayer.getHand().get(0).getValue() == 1);
        assert (testPlayer.getHand().get(2).getValue() == 3);
        assert (testPlayer.getHand().get(3).getValue() == 4);
    }


}