package cardGame;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Card class:
 * Holds inputpack data, and is
 * responsible for card distribution
 *
 * Includes get/set methods
 *
 * @author Daphne Yap & Julian Lung
 * @version 1.0
 *
 */

public class card {
    // arraylist to store input pack numbers
    private final ArrayList<Integer> inputPack = new ArrayList<>();


    public ArrayList<Integer> getInputPack(int players) throws IOException {
        ArrayList<Integer> inputPack = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        boolean valid = false;
        while (valid == false) {
            System.out.print("Enter input pack location: ");

            String file = scanner.nextLine();
            String absolutePath = (new File(file)).getAbsolutePath();
            System.out.println(absolutePath);


            //if (url != null){
            //File pathFile = new File(url.getPath());
            try (BufferedReader reader = new BufferedReader(new FileReader(absolutePath))) {
                System.out.println("Reading file...");
                String line;
                while ((line = reader.readLine()) != null) {
                    inputPack.add(Integer.parseInt(line));
                }

                if (inputPack.size() == 8 * players) {
                    System.out.println(inputPack.size());
                    // set valid = True to break loop
                    valid = true;
                } else {
                    System.out.println("Invalid: File must contain " +
                            (8 * players) + " lines, " + inputPack.size() + " found instead! ");
                    inputPack.clear();
                }
            } catch (Exception e) {
                System.out.println(e);
            }

            //}else{
            //System.out.println("File location invalid!");
            //}


        }

        return inputPack;
    }


    // distributes cards to n players
    public ArrayList<player> distributePlayers (int players, ArrayList<player>player) {

        int counter = 0;  
        for(int i = 0;i<=4*players;i++){
            int cardValue = inputPack.get(i);

            if (counter > players){
                counter = 0;
                continue;}


            else{
            player.get(counter).addToHand(cardValue);

        }
    }
        return player;
}

    // used to distribute last half of the input pack into respective player decks 
    public ArrayList<cardDeck> distributeDecks (int players, ArrayList<cardDeck>decks){
        int counter = 0;
        int nextHalf = 4*players + 1;
        for (int i = nextHalf; i<=8*players; i++){
            int cardValue = inputPack.get(i);

            if(counter > players){
                counter = 0;
                continue;}

            else{
                decks.get(counter).addToDeck(cardValue);
            }
            }
        }
    }
    

