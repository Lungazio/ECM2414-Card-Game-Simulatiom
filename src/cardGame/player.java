package cardGame;

import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Collections;

public class player implements Runnable {
    public static ArrayList<player> playerTemp = cardPortal.players;
    private ArrayList<player> winners;
    public static String playerName;
    private volatile boolean winner;
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
        String filename = "Player" + playerId + "-output.txt";
        File log_file = new File(filename);
        try {
            if (log_file.exists())
                log_file.delete();
            log_file.createNewFile();
            return filename;
        } catch (IOException e) {
            System.out.println("Couldnt create file: " + filename);
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
            if (x.getValue() == playerId + 1) {
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


    // add notify to threads
    public boolean winnerCheck() {
        ArrayList<Integer> tempHand = new ArrayList<>();
        for (card x : hand){
            tempHand.add(x.getValue());
        }
        int x = playerId;
        int occurrence = Collections.frequency(tempHand, x);
        System.out.println("occurrence :" + occurrence);
        if (occurrence == 4) {
            return true;
        }
        return false;
    }

    public void printHand() {
        String output = "hands of player" + playerId + " : " + hand.get(0).getValue();
        if (hand.size() > 1){
            for (int i = 1; i < hand.size(); i++) {
                output = output + " , " + hand.get(i).getValue();
            }
        }
        System.out.println(output);
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
        boolean win = false;

        winnerCheck();

        while(!win){
            card draw = drawDeck.drawFromDeck();
            System.out.println("player " + playerId + " draws a " + draw.getValue() + " from deck " + playerId);
            card discard = drawAndDiscard(draw);
            System.out.println("player " + playerId + " discards a " + discard.getValue() + " to deck " + discardDeck.getDeckId());
            discardDeck.discard(discard);

            printHand();
            discardDeck.printDeck();



            // write to output file
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (winnerCheck()){
                System.out.println("win player" + playerId);
                win = true;
            }
            System.out.println("\n");
        }
    }
}

