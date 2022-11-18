package cardGame;

import java.io.*;
import java.util.*;
import java.util.Collections;

public class player implements Runnable {
    private static int winner;
    public static String playerName;
    private boolean win = false;
    //player, plays as thread [WIP need to work on player behaviour]
    private final int playerId;
    private ArrayList<card> hand;


    private cardDeck drawDeck;
    private cardDeck discardDeck;

    public player(int playerId, String playerName, cardDeck drawDeck, cardDeck discardDeck) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.hand = new ArrayList<>(4);
        this.drawDeck = drawDeck;
        this.discardDeck = discardDeck;

        create_log_file();
    }

    //log file for n players
    private String create_log_file() {
        String filename = "Player" + playerId + "_output.txt";
        File log_file = new File(filename);
        try {
            if (log_file.exists())
                log_file.delete();
            log_file.createNewFile();
            return filename;
        } catch (IOException e) {
            System.out.println("Couldn't create file: " + filename);
            return null;
        }
    }

    public int getPlayerId (){
        return playerId;
    }
    ArrayList<card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<card> setHand) {
        hand = setHand;
    }

    public void addToHand(card value) {
        hand.add(value);
    }


    // implement atomic action atomic array?

    /**
     * Draws from the deck to their left
     * Chooses a non preferred card to discard
     * <p>
     * Returns discarded card value
     */



    //track moves and hands of players
    public void writeLog(String text) {
        Writer output = null;
        try {
            output = new BufferedWriter(new FileWriter("Player" + playerId + "_output.txt", true));
            output.append(text).append("\n");
            output.close();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    public String getStringHand() {
        String output = String.valueOf(hand.get(0).getValue());
        if (hand.size() > 1){
            for (int i = 1; i < hand.size(); i++) {
                output = output + " , " + hand.get(i).getValue();
            }
        }
        return output;
    }


    // add notify to threads
    public boolean winnerCheck() {
        ArrayList<Integer> tempHand = new ArrayList<>();
        for (card x : hand){
            tempHand.add(x.getValue());
        }

        int x = playerId;

        int occurrence = Collections.frequency(tempHand, x);
        if (occurrence == 4) {
            winner = x;
            return true;
        }
        return false;
    }

    public void printTurn(int draw, int discard) {
        String output = "player " + playerId + " draws a " + draw + " from deck " + playerId
                + "\nplayer " + playerId + " discards a " + discard + " to deck " + discardDeck.getDeckId()
                + "\nplayer" + playerId + " current hand is " + getStringHand();

        writeLog(output);
        System.out.println(output);
    }

    public synchronized card drawAndDiscard(card drawValue) {
        // add drawValue to current hand
        hand.add(drawValue);

        // iterate to remove card
        Iterator itr = hand.iterator();
        card x = null;
        while (itr.hasNext()) {
            // Remove first element in array that isn't the players preferred number
            // Iterator.remove()
            x = (card) itr.next();
            if (x.getValue() == playerId) {
                continue;
            } else {
                itr.remove();
                // x is the card to be discarded

                //System.out.print(elem);

                //add a card from deck
                /**
                 * 0 AS PLACEHOLDER
                 */
                break;
            }
        }
        return x;
    }

    public void setWinner(int winnerId){
        winner = winnerId;
    }

    public synchronized void end(){
        win = true;
        String output;
        if (winner == playerId){
            output = "\nplayer " + playerId + " wins"
                    + "\nplayer " + playerId + " exits"
                    + "\nplayer " + playerId + " final hand: " + getStringHand();
        }else {
            output = "\nplayer " + winner + " has informed player " + playerId + " that player " + winner + " has won"
                    + "\nplayer " + playerId + " exits"
                    + "\nplayer " + playerId + " hand: " + getStringHand();
        }
        writeLog(output);
        writeDeck();
        System.out.println(output);
    }
    public synchronized int draw(){
        card draw = drawDeck.drawFromDeck();
        hand.add(draw);

        return draw.getValue();
    }

    public synchronized int discard(){
        // iterate to remove card
        Iterator itr = hand.iterator();
        card x = null;
        while (itr.hasNext()) {
            // Remove first element in array that isn't the players preferred number
            // Iterator.remove()
            x = (card) itr.next();
            if (x.getValue() == playerId) {
                continue;
            } else {
                itr.remove();
                break;
            }
        }
        discardDeck.discard(x);

        return x.getValue();
    }

    public void writeDeck(){
        String filename = "deck" + playerId + "_output.txt";
        File log_file = new File(filename);
        Writer output;
        String deckString = drawDeck.printDeck();
        try {
            if (log_file.exists())
                log_file.delete();
            log_file.createNewFile();
            output = new BufferedWriter(new FileWriter("deck" + playerId + "_output.txt", true));
            output.append(deckString).append("\n");
            output.close();
        } catch (IOException e) {
            System.out.println("Couldn't create file: " + filename);
        }
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
    private synchronized void playerTurn() {
        int x = draw();
        int y = discard();
        printTurn(x, y);
    }

    @Override
    public void run() {
        winnerCheck();
        if (winner != 0){
            end();
        }

        while(!win){
            if (winner != 0){
                end();
            }
            else{
                playerTurn();

                winnerCheck();
                if (winner != 0){
                    end();
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

