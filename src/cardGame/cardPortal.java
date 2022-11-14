package cardGame;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.sql.Array;
import java.util.*;
import java.util.List;

public class cardPortal extends Thread implements Runnable {
    public static ArrayList<player> players = new ArrayList<>();
    public static ArrayList<cardDeck> decks = new ArrayList<>();
    private ArrayList<card> inputPack = new ArrayList<>();
    ArrayList<Thread> playerThreads = new ArrayList<>();
    ArrayList<Thread> deckThreads = new ArrayList<>();
    int numOfPlayers = 0;


    public void getPlayersInputPack() throws Exception {
        System.out.print("Enter number of players: ");
        Scanner scanner = new Scanner(System.in);

        Integer numPlayers = scanner.nextInt();
        if (numPlayers < 2) throw new IllegalArgumentException("There should be at least 2 Players.");
        else {
            numOfPlayers = numPlayers;
            //create player and decks
            for (int i = 0; i < numOfPlayers; i++) {
                String playerName = "player";
                playerName += String.valueOf(i);

                // create player object
                player temp = new player(i, playerName, new ArrayList<>());
                players.add(temp);

                // create thread for player, set name to playerid
                playerThreads.add(new Thread(temp));
                playerThreads.get(i).setName(playerName);
                //playerThreads.get(i).wait();
                // inactive until notified

                // create threads for decks
                cardDeck deckTemp = new cardDeck(i);
                decks.add(deckTemp);

                String deckName = "player";
                deckName += String.valueOf(i);
//                deckThreads.add(new Thread(deckTemp));
//                deckThreads.get(i).setName(deckName);
                //deckThreads.get(i).wait();

            }
            inputPack = getInputPack(numOfPlayers);
        }
    }

    public void playerActions() {
        boolean win = false;
        int playerId = 0;
        while (!win) {
            if (playerId == numOfPlayers - 1) {
                playerId = 0;
            }

            card drawTemp = decks.get(playerId).drawFromDeck();
            card discardTemp = players.get(playerId).drawAndDiscard(drawTemp);

            int discardDeckId = 0;
            if (playerId == numOfPlayers - 1) {
                discardDeckId = 0;
            } else {
                discardDeckId = playerId + 1;
            }
            decks.get(discardDeckId).discardToDeck(discardTemp);

            // check for win condition here


            playerId++;
        }
    }

    public void testThreads() {
        for (int i = 0; i < numOfPlayers; i++) {
            System.out.println(playerThreads.get(i).getName());
        }
    }

    public void testDiscards() {
        ArrayList<card> temp = new ArrayList<>(Arrays.asList(new card(1),new card(6),new card(8),new card(3)));
        ArrayList<card> temp2 = new ArrayList<>(Arrays.asList(new card(1),new card(6),new card(8),new card(3)));
        ArrayList<card> temp3 = new ArrayList<>(Arrays.asList(new card(1),new card(6),new card(8),new card(3)));
        ArrayList<card> temp4 = new ArrayList<>(Arrays.asList(new card(1),new card(6),new card(8),new card(3)));
//        players.get(0).setHand(temp);
//        players.get(1).setHand(temp2);
        decks.get(0).setDeck(temp3);
        decks.get(1).setDeck(temp4);


        int playerId = 0;
        for (int i = 0; i < 4; i++) {
            if (playerId == numOfPlayers) {
                playerId = 0;
            }

            System.out.println("PlayerID(" + playerId + ") initial hand: " + players.get(playerId).getHand());
            System.out.println("PlayerID(" + playerId + ") deck: " + decks.get(playerId).getCardDeck());
            card draw = decks.get(playerId).drawFromDeck();
            card discard = players.get(playerId).drawAndDiscard(draw);
            System.out.println("PlayerID(" + playerId + ") drawed a " + draw + " from deck " + playerId);

            int discardDeckId = 0;
            if (playerId == numOfPlayers - 1) {
                discardDeckId = 0;
            } else {
                discardDeckId = playerId + 1;
            }
            decks.get(playerId).discardToDeck(discard);
            System.out.println("PlayerID(" + playerId + ") discards a " + discard + " to deck " + discardDeckId);
            System.out.println("PlayerID(" + playerId + ") current hand: " + players.get(playerId).getHand() + "\n");

            //decks.get(0).generateDeckOutput(0);

            playerId++;
        }

    }

    public ArrayList<card> getInputPack(int players) throws IOException {
        Scanner scanner = new Scanner(System.in);

        boolean valid = false;
        while (valid == false) {
            System.out.print("Enter input pack location: ");

            String file = scanner.nextLine();
            String absolutePath = (new File(file)).getAbsolutePath();
            System.out.println(absolutePath);

            try (BufferedReader reader = new BufferedReader(new FileReader(absolutePath))) {
                System.out.println("Reading file...");
                String line;

                while ((line = reader.readLine()) != null) {
                    inputPack.add(new card(Integer.parseInt(line)));

                }
            } catch (Exception e) {
                System.out.println(e);
            }

            if (inputPack.size() == 8 * players) {
                // set valid = True to break loop
                valid = true;
            } else {
                System.out.println("Invalid: File must contain " +
                        (8 * players) + " lines, " + inputPack.size() + " found instead! ");
                inputPack.clear();
            }
        }
        return inputPack;
    }

    // distributes cards to n players
    // used to distribute last half of the input pack into respective player decks
    public ArrayList<cardDeck> distributeDecks(int players, ArrayList<cardDeck> decks) {
        int counter = 0;
        int nextHalf = 4 * players + 1;
        for (int i = nextHalf; i <= 8 * players; i++) {
            int cardValue = inputPack.get(i).getValue();

            if (counter > players) {
                counter = 0;
                continue;
            } else {
                decks.get(counter).addToDeck(cardValue);
            }

        }
        return decks;
    }

    public ArrayList<player> distributePlayers(int players, ArrayList<player> player) {

        int counter = 0;
        for (int i = 0; i <= 4 * players; i++) {
            card tempCard = inputPack.get(i);

            if (counter > players) {
                counter = 0;
                continue;
            } else {
                player.get(counter).addToHand(tempCard);

            }
        }
        return player;
    }

    public void startGame (){

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

