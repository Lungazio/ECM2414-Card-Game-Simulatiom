package cardGame;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.sql.Array;
import java.util.*;
import java.util.List;

public class cardPortal extends Thread implements Runnable{
    public static ArrayList<player> players = new ArrayList<>();
    public static ArrayList<cardDeck> decks = new ArrayList<>();
    ArrayList<Thread> playerThreads = new ArrayList<>();
    ArrayList<Thread> deckThreads = new ArrayList<>();
    int numOfPlayers = 0;
    card card1 = new card();


    public void getPlayersInputPack() throws Exception {
        System.out.print("Enter number of players: ");
        Scanner scanner = new Scanner(System.in);

        Integer numPlayers = scanner.nextInt();
        if (numPlayers < 2) throw new IllegalArgumentException("There should be at least 2 Players.");
        else{
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
                deckThreads.add(new Thread(deckTemp));
                deckThreads.get(i).setName(deckName);
                //deckThreads.get(i).wait();

            }
            card1.getInputPack(numOfPlayers);
        }
    }

    public void playerActions(){
        boolean win = false;
        int playerId = 0;
        while (!win){
            if (playerId == numOfPlayers-1){
                playerId = 0;
            }

            int drawValue = decks.get(playerId).drawFromDeck();
            int discardValue = players.get(playerId).drawAndDiscard(drawValue);

            int discardDeckId = 0;
            if (playerId == numOfPlayers-1){
                discardDeckId = 0;
            }else{
                discardDeckId = playerId+1;
            }
            decks.get(discardDeckId).discardToDeck(playerId);

            // check for win condition here


            playerId++;
        }
    }

    public void testThreads(){
        for (int i = 0; i < numOfPlayers; i++){
            System.out.println(playerThreads.get(i).getName());
        }
    }




    public void testDiscards(){
        ArrayList<Integer> temp = new ArrayList<>(Arrays.asList(12,2,5,2));
        ArrayList<Integer> temp2 = new ArrayList<>(Arrays.asList(1,2,4,5));
        ArrayList<Integer> temp3 = new ArrayList<>(Arrays.asList(1,6,3,7));
        ArrayList<Integer> temp4 = new ArrayList<>(Arrays.asList(1,6,9,4));
        players.get(0).setHand(temp);
        players.get(1).setHand(temp2);
        decks.get(0).setDeck(temp3);
        decks.get(1).setDeck(temp4);


        int playerId = 0;
        for (int i = 0; i < 4; i++){
            if (playerId == numOfPlayers){
                playerId = 0;
            }

            System.out.println("PlayerID("+ playerId+ ") initial hand: "+ players.get(playerId).getHand());
            System.out.println("PlayerID("+ playerId+ ") deck: "+ decks.get(playerId).getCardDeck());
            int draw = decks.get(playerId).drawFromDeck();
            int discard = players.get(playerId).drawAndDiscard(draw);
            System.out.println("PlayerID("+ playerId+ ") drawed a " + draw+ " from deck " + playerId);

            int discardDeckId = 0;
            if (playerId == numOfPlayers-1){
                discardDeckId = 0;
            }else{
                discardDeckId = playerId+1;
            }
            decks.get(playerId).discardToDeck(discard);
            System.out.println("PlayerID("+ playerId+ ") discards a "+ discard + " to deck " + discardDeckId);
            System.out.println("PlayerID("+ playerId+ ") current hand: "+ players.get(playerId).getHand() + "\n");

            //decks.get(0).generateDeckOutput(0);

            playerId++;
        }

    }

//    public void threadCreation() {
//        for (int i = 0; i < numOfPlayers; i++){
//            player thread = new player();
//        }
//    }
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

