package main;

import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

    
class CardGame{
    private Player[] players;
    private ArrayList<Player> winners;
    private volatile boolean winner;
       
    
    CardGame(int numOfPlayers, String inputPack) throws Exception{
        if (numOfPlayers < 1) throw new IllegalArgumentException("There should be more than 1 player");




        //create player
        players = new Player[numOfPlayers];
        for (int i = 0; i < numOfPlayers; i = i + 1) {
            players[i] = new Player(i, new ArrayList<>());


    
    }
}

    //player, plays as thread
    class Player implements Runnable {
        private final int playerId;

        Player(int playerId, ArrayList<Integer> card) {
            this.playerId = playerId;
            this.card = card;

            create_log_file();
         }
        
        ArrayList<Integer> getCard(){
            return card;
        }
        
        //winner check
        public Player getWinner() {
            return winners.get(index:0);
        }
        //log file baffoonery
        private String create_log_file() {
            String output = "player" + this.playerId + "_output.txt";
            File log_file = new File(output);
            try {
              if (log_file.exists())
                log_file.delete(); 
              log_file.createNewFile();
              return output;
            } catch (IOException e) {
              System.out.println("Couldnt create file " + output);
              return null;}
              
            } 


            private void rotations() {
                synchronized (Player.class) {
                    if (winner) {
                        return;
                    }


    }
}

