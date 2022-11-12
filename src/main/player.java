package main;
import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class player {

    private Player[] players;
    private ArrayList<Player> winners;
    private volatile boolean winner;



    //player, plays as thread [WIP need to work on player behaviour]
    class Player implements Runnable {
        
        private final int playerId;
        private ArrayList<Integer> hand;

        public Player(int playerId, ArrayList<Integer> hand) {
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
        

        Iterator itr = al.iterator();

		// Holds true till there is single element
		// remaining in the object
		while (itr.hasNext()) {

			// Remove elements smaller than 10 using
			// Iterator.remove()
			int x = (Integer)itr.next();
			if (x == 2){
				continue;}
                else {itr.remove();
                int elem = x;
                System.out.print(elem);
                break;}
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


    
        //winner check 
        public Player winnerCheck() {

            return winners.get(0);
            }
    
}
}
