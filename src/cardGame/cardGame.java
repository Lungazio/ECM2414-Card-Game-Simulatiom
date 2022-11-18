package cardGame;

import java.io.*;
import java.util.*;

/**
 * cardGame class
 * Retrieves and validates all necessary input,
 * initializes all players and distributes cards,
 * performs preliminary checks before starting the game.
 *
 *
 * @author Daphne Yap & Julian Lung
 * @version 5.0
 *
 */
public class cardGame extends Thread implements Runnable {
    // ArrayList to hold current players
    public static ArrayList<player> players = new ArrayList<>();
    public static ArrayList<cardDeck> decks = new ArrayList<>();
    private ArrayList<card> inputPack = new ArrayList<>();
    int numOfPlayers = 0;


    /**
     * Retrieves number of players
     * and input pack location from user
     *
     * Initializes players and decks
     */
    public void getPlayersInputPack() throws IllegalArgumentException, IOException {
        // throw exception if number of players are less than 2
        boolean valid = false;
        while (!valid){
            System.out.print("Enter number of players: ");
            Scanner scanner = new Scanner(System.in);
            Integer numPlayers = scanner.nextInt();

            if (numPlayers < 2) throw new IllegalArgumentException("There should be at least 2 Players.");
            else {
                valid = true;
                numOfPlayers = numPlayers;

                //create decks and players
                for (int i = 0; i < numOfPlayers; i++){
                    cardDeck deckTemp = new cardDeck(i +1);
                    decks.add(deckTemp);
                }

                int discardDeckId;

                // create players, and set the discard deck ids accordingly
                // discard deck to the right, discard deck id = current + 1
                for (int i = 0; i < numOfPlayers; i++) {
                    if (i == numOfPlayers-1){
                        discardDeckId = 0;
                    }
                    else{
                        discardDeckId = i+1;
                    }

                    // create new player object and store in ArrayList
                    player temp = new player(i + 1, decks.get(i), decks.get(discardDeckId));
                    players.add(temp);
                }
            }
        }
        // call get input pack function
        getInputPack();
    }

    /**
     * Gets input pack location from user input
     * Verifies if input pack is valid before
     * reading and storing.
     *
     * Input pack should be size of
     * 8 * number of players
     *
     * @return An ArrayList of cards
     *
     * Used in every playerTurn
     */
    public ArrayList<card> getInputPack() throws IOException {
        Scanner scanner = new Scanner(System.in);
        boolean valid = false;

        // continue looping if file not found or is invalid
        while (valid == false) {
            System.out.print("Enter input pack location: ");

            // read file location string
            String file = scanner.nextLine();
            String absolutePath = (new File(file)).getAbsolutePath();
            System.out.println(absolutePath);

            // read file
            try (BufferedReader reader = new BufferedReader(new FileReader(absolutePath))) {
                System.out.println("Reading file...");
                String line;

                // try parsing each int line and store in inputpack
                while ((line = reader.readLine()) != null) {
                    inputPack.add(new card(Integer.parseInt(line)));
                }
            }
            // print stack trace if exceptions found
            catch (Exception e) {
                e.printStackTrace();
            }

            // check size of input pack
            if (inputPack.size() == 8 * numOfPlayers) {
                // set valid = True to break loop
                valid = true;
            }

            // size does not match 8 * number of players, clear input pack
            // rerun while loop
            else {
                System.out.println("Invalid: File must contain " +
                        (8 * numOfPlayers) + " lines, " + inputPack.size() + " found instead! ");
                inputPack.clear();
            }
        }
        return inputPack;
    }

    /**
     * Distributes last half of the input pack
     * into respective player decks in a
     * round-robin fashion
     *
     * @return ArrayList containing all decks
     *
     *
     * Used after distributing to players
     *          before the start of the game
     */
    public ArrayList<cardDeck> distributeDecks() {
        int counter = 0;

        // loop through second half of input pack
        for (int i = 4 * numOfPlayers; i < 8 * numOfPlayers; i++) {
            card currentCard = inputPack.get(i);

            // counter to loop through each player
            if (counter == numOfPlayers) {
                counter = 0;
            }

            // add current card to deck
            decks.get(counter).addToDeck(currentCard);

            counter++;
        }
        return decks;
    }

    /**
     * Distributes first half of the input pack
     * into respective players in a
     * round-robin fashion
     *
     * @return ArrayList containing all players
     *
     * Used before distributing to decks
     *          before the start of the game
     */
    public ArrayList<player> distributePlayers() {
        int counter = 0;

        // loop through first half of inputpack
        for (int i = 0; i < 4 * numOfPlayers; i++) {
            card currentCard = inputPack.get(i);

            // counter to loop through each player
            if (counter == numOfPlayers) {
                counter = 0;
            }

            // add current card to players hand
            players.get(counter).addToHand(currentCard);

            counter++;
        }
        return players;
    }

    /**
     * Outputs all players' hands in a readable string
     * and writes initial hands to player logs
     *
     * Used before the start of the game
     */
    public void printInitialHand(){
        // loop through all players
        for (player p : players){
            // format string
            String output = "player " + p.getPlayerId() + " initial hand: " + p.getStringHand();

            // write to log
            p.writeLog(output);
            System.out.println(output);
        }
    }

    /**
     * Start all player threads
     *
     * Starts the game, players will
     * start drawing and discard
     */
    public void startPlayers() {
        // loop through all in player ArrayList
        for (player p: players){
            // create player as a runnable thread and start
            Thread playerThread = new Thread(p);
            playerThread.start();
        }
    }

    /**
     * Checks all players' hands
     *
     * Regardless of player's preferred card denomination,
     * if a player has 4 of the same cards, they win
     *
     * @return true if a player has a winning hand
     */
    public boolean winnerCheck() throws IOException {
        // loop through all players
        for (player p : players){

            // convert player hand into an int ArrayList for easy checks
            ArrayList<Integer> currentHand = new ArrayList<>();
            for (card x : p.getHand()){
                currentHand.add(x.getValue());
            }

            // get first card of the hand
            int cardValue;
            cardValue = currentHand.get(0);

            // check number of times cardValue is in currentHand
            int occurrence = Collections.frequency(currentHand, cardValue);

            // a winning hand
            if (occurrence == 4) {
                // set winner to current player ID
                p.setWinner(p.getPlayerId());
                p.end();

                // end the game for all players
                for (player p1 : players) {
                    if (p1.getPlayerId() != p.getPlayerId()) {
                        p1.end();
                    }
                }
                return true;
            }
        }
        return false;
    }


    public static void main (String[] args) throws Exception {
        cardGame cardRun = new cardGame();

        // initialize game
        cardRun.getPlayersInputPack();

        // distribute card to players and deck
        ArrayList<player> players = cardRun.distributePlayers();
        ArrayList<cardDeck> deck = cardRun.distributeDecks();

        // print initial hand and check for winners
        cardRun.printInitialHand();
        // no winners : start game
        if (!cardRun.winnerCheck()) {
            cardRun.startPlayers();
        }
    }
}

