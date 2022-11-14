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

    public player(int playerId,  String playerName, ArrayList<card> hand) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.hand= new ArrayList<>(4);

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


    ArrayList<card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<card> setHand){
        hand = setHand;
    }

    public void addToHand (card value){
        hand.add(value);
    }


    // implement atomic action atomic array?
    /**
     * Draws from the deck to their left
     * Chooses a non preferred card to discard
     *
     * Returns discarded card value
     */
    public card drawAndDiscard(card drawValue) {
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



    //winner check
    public boolean winnerCheck(ArrayList<player>players) {
        return players.isEmpty() || Collections.frequency(players, players.get(0)) == players.size();
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
    @Override
    public void run() {
        //drawAndDiscard();
    }
}


