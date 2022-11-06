package main;

import java.util.Scanner;
/**
 * cardGame class:
 *
 *
 * Includes get/set methods
 *
 * @author Daphne Yap & Julian Lung
 * @version 1.0
 *
 */


import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

//still needs the rest of the classes
class CardGame {

    private Player[] players;
    private ArrayList<Player> winners;
    private volatile boolean winner;

    
    CardGame(int numOfPlayers, String inputPack) throws Exception {
        if (numOfPlayers < 2) throw new IllegalArgumentException("There should be at least 2 Players.");


        //create player
        players = new Player[numOfPlayers];
        for (int i = 0; i < numOfPlayers; i = i + 1) {
            players[i] = new Player(i, new ArrayList<>());


        }
    }



    //start player thread
    void run() {
        System.out.println("Begin game:");

        ArrayList<Thread> threads = new ArrayList<>();
        for (Player player : players) {
            Thread t = new Thread(player);
            t.start();
            threads.add(t);
        }

        // wait for thread
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException exc) {
                exc.printStackTrace();
            }
        }
        // announce winner
        System.out.println("Winner: " + winnerCheck());
        System.out.println("Game over.");
    }



    //player, plays as thread [WIP need to work on player behaviour]
    class Player implements Runnable {
        
        private final int playerId;
        private ArrayList<Integer> card;

        Player(int playerId, ArrayList<Integer> card) {
            this.playerId = playerId;
            this.card = card;

            create_log_file();
        
        }
        //log file for n players
        private String create_log_file() {
            String filename = "Player" + playerId + "-output.txt";
            File log_file = new File(filename);
            try {
              if (log_file.exists())
                log_file.delete(); 
              log_file.createNewFile();
              return filename;
            } catch (IOException e) {
              System.out.println("Couldnt create file: " + filename);
              return null;}
              
            }
        

        ArrayList<Integer> getCard() {
            return card;
        }
    


    
        //go around the table [WIP]
        private void rotations() {
            synchronized (Player.class) {
                if (winner) {
                return;
                }
            }
        }


        //track moves and hands of players
        private void writeLog(String text) {
            System.out.println(text);

            Writer output = null;
            try {
                output = new BufferedWriter(new FileWriter("Player" + playerId + "_output.txt", true));
                output.append(text).append("\n");
                output.close();
            } catch (IOException err) {
                err.printStackTrace();
            }
        }


    }
        //winner check 
        public Player winnerCheck() {

            return winners.get(0);
            }





}


