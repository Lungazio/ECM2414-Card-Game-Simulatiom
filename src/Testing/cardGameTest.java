package Testing;

import cardGame.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class cardGameTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @org.junit.Test
    public void initializeGame() {

    }

    @org.junit.Test
    public void initializePlayers() {
        assertTrue(cardGame.initializePlayers(2));
        exception.expect(IllegalArgumentException.class);
        assertFalse(cardGame.initializePlayers(-1));

        cardGame.reset();
    }

    @org.junit.Test
    public void readInputPack() throws IOException {
        String invalidPack = "abc";
        String twoPlayerPack = "src/inputpack.txt";
        String fourPlayerPack = "src/inputpack4.txt";

        cardGame.initializePlayers(2);

        assertFalse(cardGame.readInputPack(invalidPack));
        assertTrue(cardGame.readInputPack(twoPlayerPack));
        assertFalse(cardGame.readInputPack(fourPlayerPack));


        cardGame.initializePlayers(4);
        assertTrue(cardGame.readInputPack(fourPlayerPack));

        cardGame.reset();
    }

    @org.junit.Test
    public void inputPackCheck() throws IOException {

        cardGame.initializePlayers(2);
        cardGame.readInputPack("src/inputpack4.txt");
        ArrayList<card> inputPack4 = cardGame.inputPack;
        System.out.println(inputPack4.size());
        assertFalse(cardGame.inputPackCheck(inputPack4));

        cardGame.readInputPack("src/inputpack.txt");
        ArrayList<card> inputPack2 = cardGame.inputPack;
        System.out.println(inputPack2.size());
        assertTrue(cardGame.inputPackCheck(inputPack2));

        cardGame.reset();
    }

    @org.junit.Test
    public void distributeDecks() throws IOException {
        // distribute decks for 2 players
        startTwoPlayerGame();
        ArrayList<cardDeck> decks = cardGame.distributeDecks();

        // total number of decks are equal to player number
        assert (decks.size() == 2);

        // deck ids are correctly set
        assert (decks.get(0).getDeckId() == 1);
        assert (decks.get(1).getDeckId() == 2);

        // assert all decks have 4 cards
        assert (decks.get(0).getDeck().size() == 4);
        assert (decks.get(1).getDeck().size() == 4);

        cardGame.reset();
    }

    @org.junit.Test
    public void distributePlayers() throws IOException {
        startFourPlayerGame();
        ArrayList<player> players = cardGame.distributePlayers();

        assert (players.size() == 4);
        assert (players.get(0).getHand().size() == 4);
        assert (players.get(1).getHand().size() == 4);
        assert (players.get(2).getHand().size() == 4);
        assert (players.get(3).getHand().size() == 4);

        cardGame.reset();
    }

    @org.junit.Test
    public void printInitialHand() throws IOException, InterruptedException {
        cardGame.reset();
        startTwoPlayerGame();
        cardGame.distributePlayers();
        cardGame.distributeDecks();
        cardGame.printInitialHand();

        try {
            for (player p : cardGame.players) {
                // get player output file
                Path file = Paths.get("player" + p.getPlayerId() + "_output.txt");

                // lines in player output file should be 1
                // which is players' initial hand
                assert (Files.lines(file).count() == 1);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }

        cardGame.reset();
    }

    @org.junit.Test
    public void startPlayers() throws IOException {
        int num = startTwoPlayerGame();

        cardGame.distributePlayers();
        cardGame.distributeDecks();

        int threads = Thread.getAllStackTraces().keySet().size();
        cardGame.startPlayers();
        int currentThreads = Thread.getAllStackTraces().keySet().size();

        for (int i = 0; i < num; i++){
            cardGame.players.get(i).end();
        }

        // since there are 2 players,
        assert (currentThreads == threads + num);

        cardGame.reset();
    }

    @org.junit.Test
    public void winnerCheck() throws IOException {
        startTwoPlayerGame();
        cardGame.distributePlayers();
        cardGame.distributeDecks();

        assertFalse(cardGame.winnerCheck());

        ArrayList<card> player1 = new ArrayList<>(Arrays.asList(new card(2), new card(2), new card(2), new card(2)));
        cardGame.players.get(0).setHand(player1);
        assertTrue(cardGame.winnerCheck());

        cardGame.reset();
    }

    @org.junit.Test
    public void main() {

    }

    @org.junit.Test
    public void clearGame() {
        cardGame.reset();
        assert (cardGame.players.size() == 0);
        assert (cardGame.decks.size() == 0);
        assert (cardGame.inputPack.size() == 0);
    }

    private int startTwoPlayerGame() throws IOException {
        cardGame.initializePlayers(2);
        cardGame.readInputPack("src/inputpack.txt");

        return 2;
    }

    private void startFourPlayerGame() throws IOException {
        cardGame.initializePlayers(4);
        cardGame.readInputPack("src/inputpack4.txt");
    }
}