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

            for (int i = 0; i < numOfPlayers; i++){
                cardDeck deckTemp = new cardDeck(i +1);
                decks.add(deckTemp);

            }
            int discardDeckId;
            for (int i = 0; i < numOfPlayers; i++) {
                String playerName = "player";
                playerName += String.valueOf(i +1);

                // create player object
                if (i == numOfPlayers-1){
                    discardDeckId = 0;
                }
                else{
                   discardDeckId = i;
                }
                player temp = new player(i + 1, playerName, decks.get(i), decks.get(discardDeckId));
                players.add(temp);

                // create thread for player, set name to playerid
                playerThreads.add(new Thread(temp));
                playerThreads.get(i).setName(playerName);
                //playerThreads.get(i).wait();

            }
            inputPack = getInputPack(numOfPlayers);
        }
    }


    public void testThreads() {
        for (int i = 0; i < numOfPlayers; i++) {
            System.out.println(playerThreads.get(i).getName());
        }
    }

//    public void testDiscards() {
//        ArrayList<card> temp = new ArrayList<>(Arrays.asList(new card(1),new card(6),new card(8),new card(3)));
//        ArrayList<card> temp2 = new ArrayList<>(Arrays.asList(new card(1),new card(6),new card(8),new card(3)));
//        ArrayList<card> temp3 = new ArrayList<>(Arrays.asList(new card(1),new card(6),new card(8),new card(3)));
//        ArrayList<card> temp4 = new ArrayList<>(Arrays.asList(new card(1),new card(6),new card(8),new card(3)));
////        players.get(0).setHand(temp);
////        players.get(1).setHand(temp2);
//        decks.get(0).setDeck(temp3);
//        decks.get(1).setDeck(temp4);
//
//
//        int playerId = 0;
//        for (int i = 0; i < 4; i++) {
//            if (playerId == numOfPlayers) {
//                playerId = 0;
//            }
//
//            System.out.println("PlayerID(" + playerId + ") initial hand: " + players.get(playerId).getHand());
//            System.out.println("PlayerID(" + playerId + ") deck: " + decks.get(playerId).getCardDeck());
//            card draw = decks.get(playerId).drawFromDeck();
//            card discard = players.get(playerId).drawAndDiscard(draw);
//            System.out.println("PlayerID(" + playerId + ") drawed a " + draw + " from deck " + playerId);
//
//            int discardDeckId = 0;
//            if (playerId == numOfPlayers - 1) {
//                discardDeckId = 0;
//            } else {
//                discardDeckId = playerId + 1;
//            }
//            decks.get(playerId).discardToDeck(discard);
//            System.out.println("PlayerID(" + playerId + ") discards a " + discard + " to deck " + discardDeckId);
//            System.out.println("PlayerID(" + playerId + ") current hand: " + players.get(playerId).getHand() + "\n");
//
//            //decks.get(0).generateDeckOutput(0);
//
//            playerId++;
//        }
//
//    }

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
    public ArrayList<cardDeck> distributeDecks() {
        int counter = 0;
        for (int i = 4 * numOfPlayers; i < 8 * numOfPlayers; i++) {
            int cardValue = inputPack.get(i).getValue();

            if (counter == numOfPlayers) {
                counter = 0;
            }
            decks.get(counter).addToDeck(cardValue);

            counter++;
        }
        return decks;
    }

    public ArrayList<player> distributePlayers() {

        int counter = 0;
        for (int i = 0; i < 4 * numOfPlayers; i++) {
            card tempCard = inputPack.get(i);

            if (counter == numOfPlayers) {
                counter = 0;
            }

            players.get(counter).addToHand(tempCard);

            counter++;
        }
        return players;
    }

    public int winnerCheck(){
        ArrayList<card> temp = new ArrayList<>(Arrays.asList(new card(1), new card(1), new card(1), new card(1)));
        players.get(1).setHand(temp);
        for (int i = 0; i <numOfPlayers; i++){
            players.get(i).printHand();
            if (players.get(i).winnerCheck()){
                System.out.println("PLAYER " + i + " HAS WON!!!!");
                players.get(i).printHand();
                return i;
            }
        }
        return 0;
    }

    public void startPlayers() {
        for (player p: players){
            p.run();
        }
    }

    public static void main (String[] args) throws Exception {
        cardPortal cardTestRun = new cardPortal();

        // initalize game
        cardTestRun.getPlayersInputPack();
        ArrayList<player> players = cardTestRun.distributePlayers();
        ArrayList<cardDeck> deck = cardTestRun.distributeDecks();
        players.get(0).printHand();
        players.get(1).printHand();
        deck.get(0).printDeck();
        deck.get(1).printDeck();

        // win checks before starting the game
        cardTestRun.winnerCheck();

        cardTestRun.startPlayers();

    }
}

