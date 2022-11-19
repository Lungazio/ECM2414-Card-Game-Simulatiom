package cardGame;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.sql.Array;
import java.util.*;
import java.util.List;

public class cardGame extends Thread implements Runnable {
    public static ArrayList<player> players = new ArrayList<>();
    public static ArrayList<cardDeck> decks = new ArrayList<>();
    private ArrayList<card> inputPack = new ArrayList<>();
    int numOfPlayers = 0;

    public void getPlayersInputPack() throws Exception {

        //Asks for user input and checks if it is valid else throw exception
        System.out.print("Enter number of players: ");
        Scanner scanner = new Scanner(System.in);

        Integer numPlayers = scanner.nextInt();
        if (numPlayers < 2) throw new IllegalArgumentException("There should be at least 2 Players.");
        else {
            numOfPlayers = numPlayers;
            //create player and decks

            for (int i = 0; i < numOfPlayers; i++){
                cardDeck deck = new cardDeck(i +1);
                decks.add(deck);
            }

            int discardDeckId;
            for (int i = 0; i < numOfPlayers; i++) {
                String playerName = "player";
                playerName += String.valueOf(i +1);

                // when reaches last player, last player will dicard to first deck in arraylist
                if (i == numOfPlayers-1){
                    discardDeckId = 0;
                }
                // player discards to next hand
                else{
                   discardDeckId = i+1;
                }

                player playerJoin = new player(i + 1, decks.get(i), decks.get(discardDeckId));
                players.add(playerJoin);

                // create thread for player, set name to playerid

            }
            inputPack = getInputPack(numOfPlayers);
        }
    }


//Reads and gets cards from the input pack
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
            card cardValue = inputPack.get(i);

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
            card cardNum = inputPack.get(i);

            if (counter == numOfPlayers) {
                counter = 0;
            }

            players.get(counter).addToHand(cardNum);

            counter++;
        }
        return players;
    }

    //prints the players starting hand
    public void printInitialHand(){
        for (player p : players){
            String output = "player " + p.getPlayerId() + " initial hand: " + p.getStringHand();
            p.writeLog(output);

            System.out.println(output);
        }
    }
    
    //starts player threads
    public void startPlayers() {
        for (player p: players){
            Thread playerThread = new Thread(p);
            playerThread.start();
        }
    }


    // checks if a player has a winning hand by checking the number of occurences of a card value in their hands
    public boolean winnerCheck() {

        for (player p : players){
            ArrayList<Integer> winHand = new ArrayList<>();
            for (card x : p.getHand()){
                winHand.add(x.getValue());
            }
            int x;
            x = p.getHand().get(0).getValue();
            int occurrence = Collections.frequency(winHand, x);
            if (occurrence == 4) {
                p.setWinner(p.getPlayerId());
                for (player p1 : players) {
                    p1.end();
                }
                return true;
                //
            }
        }
        return false;
    }

    public static void main (String[] args) throws Exception {
        cardGame cardTestRun = new cardGame();

        // initalize game

        cardTestRun.getPlayersInputPack();
        ArrayList<player> players = cardTestRun.distributePlayers();
        ArrayList<cardDeck> deck = cardTestRun.distributeDecks();

        cardTestRun.printInitialHand();
        if (!cardTestRun.winnerCheck()) {
            cardTestRun.startPlayers();
        }
//
//        while (!ended){
//            cardTestRun.winnerCheck();
//        }

//        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
//        System.out.println(threadSet);
    }
}

