package cardGame;

import java.io.*;
import java.util.*;
import java.util.Collections;

public class player implements Runnable {
    private static int winner;  //winner ID used for the winner check
    private boolean win = false; //used to check whether the game ends 
    private final int playerId; //Assign to players for distribution of cards, hands and winner check
    private ArrayList<card> hand; //Array to hold card values of players
    private cardDeck drawDeck;
    private cardDeck discardDeck;

    //log file for n players and places into output file respective to playerID
    public player(int playerId, cardDeck drawDeck, cardDeck discardDeck) {
        this.playerId = playerId;
        this.hand = new ArrayList<>(4); 
        this.drawDeck = drawDeck;
        this.discardDeck = discardDeck;

        create_log_file();
    }

    // Make logs and deletes old logs from previous games if it exists
    public String create_log_file() {
        String filename = "Player" + playerId + "_output.txt";
        File log_file = new File(filename);
        try {
            if (log_file.exists())
                log_file.delete();
            log_file.createNewFile();
            return filename;
            
            //throw an exception if failed to make log
        } catch (IOException e) {
            System.out.println("Couldn't create file: " + filename);
            return null;
        }

    }

    public int getPlayerId (){
        return playerId;
    }

    public ArrayList<card> getHand() {
        return hand;
    }


    public void addToHand(card value) {
        hand.add(value);
    }


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
    

    //used for printing of player hands by turning values to string
    public String getStringHand() {
        String output = String.valueOf(hand.get(0).getValue());
        if (hand.size() > 1){
            for (int i = 1; i < hand.size(); i++) {
                output = output + " , " + hand.get(i).getValue();
            }
        }
        return output;
    }


    // used to reuturn a boolean whether a player had won or not
    public boolean winnerCheck() {
        ArrayList<Integer> winnerHand = new ArrayList<>();
        for (card x : hand){
            winnerHand.add(x.getValue());
        }

        int Id = playerId;
        // check occurences of a certain card value of a winner hand when 4 of a kind are found returns True 
        int occurrence = Collections.frequency(winnerHand, Id); //used to find frequency of a value 
        if (occurrence == 4) {
            winner = Id;
            return true;
        }
        return false;
    }


    //Prints player's actions in a turn
    public void printTurn(int draw, int discard) {
        String output = "player " + playerId + " draws a " + draw + " from deck " + playerId
                + "\nplayer " + playerId + " discards a " + discard + " to deck " + discardDeck.getDeckId()
                + "\nplayer" + playerId + " current hand is " + getStringHand();

        writeLog(output);
        System.out.println(output);
    }
 

    public void setWinner(int winnerId){
        winner = winnerId;
    }

    public int getWinnerID(){
        return winner;
    }

    //Ends the threads when winner is found, Print out decks of winner and losers as well as player dialogue
    public void end(){
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
        System.out.println(output);
    }

    //Used to draw from top of deck/first element of array list
    public synchronized int draw(){
        //gets a value from top deck and puts into player hand
        card draw = drawDeck.drawFromDeck();
        hand.add(draw);

        return draw.getValue();
    }

    //Used for player's action of discarding along with behavious of preferring values of their playerID
    public synchronized int discard(){
        // iterate to remove card
        Iterator itr = hand.iterator();
        card removedCard = null;
        while (itr.hasNext()) {
            // Remove first element in array that isn't the players preferred number
            // Iterator.remove()
            removedCard = (card) itr.next();
            if (removedCard.getValue() == playerId) {
                continue;
            }
             // x is the card to be discarded

            //when a card is found to not be preferred by the player dicard it from their hand and break the loop and return X
            //value X to be given to the playerdeck 

            else {
                itr.remove();
                break;
            }
        }
        discardDeck.discard(removedCard);

        return removedCard.getValue();
    }

    
    //runs the game initially checking for any winner, ending the game before it starts if so, sleep timer for easier viewing of thread actions
    @Override
    public void run() {
        winnerCheck();
        if (winner != 0){
            end();
        }

        //checks if there is any winner and ends if something is found
        while(!win){
            if (winner != 0){
                end();
            }
            //prints the draw and discard of players
            else{
                int draw = draw();
                int discard = discard();

                printTurn(draw, discard);

                //checks for winners signalling to end if not 0
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
//src/inputpack.txt
        }
    }
}

