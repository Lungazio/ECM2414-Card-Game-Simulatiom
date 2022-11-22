package cardGame;

import java.io.*;
import java.util.*;

public class cardGame extends Thread implements Runnable {
    // initialize ArrayLists for players, decks and input pack.
    public static ArrayList<player> players = new ArrayList<>();
    public static ArrayList<cardDeck> decks = new ArrayList<>();
    public static ArrayList<card> inputPack = new ArrayList<>();
    static int numOfPlayers = 0;


    // asks for input until number of players and file is valid
    public static void initializeGame() throws IOException {
        boolean valid = false;

        // while loop for number of players
        while (!valid) {
            System.out.print("Enter number of players: ");
            Scanner scanner = new Scanner(System.in);
            int num = scanner.nextInt();

            // if players are initialized without issues, break
            if (initializePlayers(num)) {
                valid = true;
            }
        }

        valid = false;
        // while loop for input pack location
        while (!valid) {
            System.out.print("Enter input pack location: ");
            Scanner scanner = new Scanner(System.in);
            String location = scanner.nextLine();

            // if input pack is read successfully, break
            if (readInputPack(location)) {
                valid = true;
            }
        }
    }

    // reset all ArrayLists to 0 for a fresh game
    // used in testing
    public static void reset() {
        players.clear();
        decks.clear();
        numOfPlayers = 0;
        inputPack.clear();
    }


    // creates decks and players if user inputs valid number of players
    public static boolean initializePlayers(int num) throws IllegalArgumentException {
        // throw exception if number is invalid
        if (num < 2) {
            throw new IllegalArgumentException("There should be at least 2 Players.");
        } else {
            numOfPlayers = num;
            //create player and decks

            // create number of decks equivalent to number of players
            for (int i = 0; i < numOfPlayers; i++) {
                cardDeck deck = new cardDeck(i + 1);
                decks.add(deck);
            }

            int discardDeckId;

            // create players
            for (int i = 0; i < numOfPlayers; i++) {
                // last player will discard to the deck of player1
                if (i == numOfPlayers - 1) {
                    discardDeckId = 0;
                }
                // player discards to next deck
                else {
                    discardDeckId = i + 1;
                }

                // create new player and add to players ArrayList
                player playerJoin = new player(i + 1, decks.get(i), decks.get(discardDeckId));
                players.add(playerJoin);
            }

            // players successfully created
            return true;
        }
    }

    // validates, reads and gets cards from the input pack
    // returns true if success
    public static boolean readInputPack(String location) throws IOException {
        File fileCheck = new File(location);

        // check if file can be found
        if (fileCheck.isFile()) {
            // read from file
            String absolutePath = fileCheck.getAbsolutePath();
            try (BufferedReader reader = new BufferedReader(new FileReader(absolutePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // add to input pack ArrayList as a new card object
                    inputPack.add(new card(Integer.parseInt(line)));
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            // run size check
            if (inputPackCheck(inputPack)) {
                return true;
            }
        } else {
            System.out.println("Invalid file location");
            return false;
        }
        return false;

    }

    // boolean check that input pack is size 8 * n
    public static boolean inputPackCheck(ArrayList<card> inputPack) {
        if (inputPack.size() == 8 * numOfPlayers) {
            return true;
        } else {
            // display message
            System.out.println("Invalid: File must contain " +
                    (8 * numOfPlayers) + " lines, " + inputPack.size() + " found instead! ");
            inputPack.clear();
            return false;
        }
    }


    // distribute cards to n decks
    // used to distribute last half of the input pack into respective player decks
    public static ArrayList<cardDeck> distributeDecks() {
        int counter = 0;

        // loop through last half of input pack
        for (int i = 4 * numOfPlayers; i < 8 * numOfPlayers; i++) {
            card cardValue = inputPack.get(i);

            // counter to implement round-robin distribution
            if (counter == numOfPlayers) {
                counter = 0;
            }

            // add card to appropriate decks
            decks.get(counter).addToDeck(cardValue);

            counter++;
        }
        return decks;
    }


    // distribute cards to n players
    // used to distribute first half of the input pack into respective player hands
    public static ArrayList<player> distributePlayers() {
        int counter = 0;

        // loop through last half of input pack
        for (int i = 0; i < 4 * numOfPlayers; i++) {
            card cardNum = inputPack.get(i);

            // counter to implement round-robin distribution
            if (counter == numOfPlayers) {
                counter = 0;
            }

            // add card to appropriate player hands
            players.get(counter).addToHand(cardNum);

            counter++;
        }
        return players;
    }

    // prints the players starting hand
    public static void printInitialHand() {
        // print every players' hand and write to log
        for (player p : players) {
            String output = "player " + p.getPlayerId() + " initial hand: " + p.printHand();
            p.writeLog(output);

            System.out.println(output);
        }
    }

    // starts player threads
    public static void startPlayers() {
        for (player p : players) {
            Thread playerThread = new Thread(p);
            playerThread.start();
        }
    }


    // checks if a player has a winning hand by checking the number of occurrences
    // of a card value in their hands
    public static boolean winnerCheck() {
        for (player p : players) {
            // convert hand to ArrayList<Integer> for easy checking
            ArrayList<Integer> currentHand = new ArrayList<>();
            for (card x : p.getHand()) {
                currentHand.add(x.getValue());
            }

            // set value to be compared
            int x;
            x = p.getHand().get(0).getValue();

            // check for occurrences
            int occurrence = Collections.frequency(currentHand, x);
            if (occurrence == 4) {
                // set winner to current player and end the game for everyone
                p.setWinner(p.getPlayerId());
                for (player p1 : players) {
                    p1.end();
                }
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        cardGame cardTestRun = new cardGame();

        // initialize game
        cardTestRun.initializeGame();
        cardTestRun.distributePlayers();
        cardTestRun.distributeDecks();

        cardTestRun.printInitialHand();
        if (!cardTestRun.winnerCheck()) {
            cardTestRun.startPlayers();
        }
    }


}

