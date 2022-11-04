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


class CardGame {

    private Player[] players;
    private ArrayList<Player> winners;
    private volatile boolean winner;


    CardGame(int numOfPlayers, String inputPack) throws Exception {
        if (numOfPlayers < 1) throw new IllegalArgumentException("There should be more than 1 player");


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

        // wait for all threads to finish
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



    //player, plays as thread
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
            String output = "Player" + playerId + "_output.txt";
            File log_file = new File(output);
            try {
              if (log_file.exists())
                log_file.delete(); 
              log_file.createNewFile();
              return output;
            } catch (IOException e) {
              System.out.println("Couldnt create file: " + output);
              return null;}
              
            }
        

        ArrayList<Integer> getCard() {
            return card;
        }
    


    
        //go around the table
        private void rotations() {
            synchronized (Player.class) {
                if (winner) {
                return;
                }
            }
        }


        //track moves and hands of players
        private void log(String text) {
            System.out.println(text);

            Writer output = null;
            try {
                output = new BufferedWriter(new FileWriter("Player" + playerId + "_output.txt", true));
                output.append(text).append("\n");
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
        //winner check
        public Player winnerCheck() {

            return winners.get(0);
            }





}


