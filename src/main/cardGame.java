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
         }
        
        ArrayList<Integer> getCard(){
            return card
        }
        
        //winner check
        public Player getWinner() {
            return winners.get(0);


    }
}

