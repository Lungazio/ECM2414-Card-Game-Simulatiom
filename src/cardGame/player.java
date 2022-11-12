package cardGame;
import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class player implements Runnable {

    private player[] players;
    private ArrayList<player> winners;
    private volatile boolean winner;
    //player, plays as thread [WIP need to work on player behaviour]
    private final int playerId;
    private ArrayList<Integer> hand;

    public player(int playerId, ArrayList<Integer> hand) {
        this.playerId = playerId;
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


    ArrayList<Integer> getHand() {
        return hand;
    }

    // implement atomic action atomic array?
    private int discardAndDraw(){
        Iterator itr = hand.iterator();
        while (itr.hasNext()) {

            // Remove first element in array that isnt the players preferred number
            // Iterator.remove()
            int x = (Integer)itr.next();
            if (x == playerId){
                continue;}
                else {itr.remove();
                int elem = x;
                //System.out.print(elem);

                //add a card from deck
                /**
                 * 0 AS PLACEHOLDER
                 */
                hand.add(0);//need a func from deck that removes cards to be called
                break;}

        }
        return 0;
    }





    //go around the table [WIP]
    private void rotations() {
        synchronized (player.class) {
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



    //winner check
    public player winnerCheck() {
        return winners.get(0);
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

    }
}


