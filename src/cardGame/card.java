package cardGame;

import java.io.*;
import java.net.URL;
import java.nio.file.*;
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
    private final ArrayList<Integer> inputPack = new ArrayList<>();


    public ArrayList<Integer> getInputPack(int players) throws IOException {
        Scanner scanner = new Scanner(System.in);


        boolean valid = false;
        while (valid == false) {
            System.out.print("Enter input pack location: ");

            String file = scanner.nextLine();
            URL url = getClass().getResource(file);
            File pathFile = new File(url.getPath());


            try (BufferedReader reader = new BufferedReader(new FileReader(pathFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    inputPack.add(Integer.parseInt(line));
                }

                reader.close();
            } catch (Exception e) {
                System.out.println(e);
            }


//            // file exists, read file and break while loop
//            // initialize scanner
//            Scanner scanner = new Scanner(new File(String.valueOf(file_path)));
//
//            /** CHECK IF INPUT PACK LINES = 8N **/
//            long lines = Files.lines(file_path).count();
//            System.out.println(lines);
//
//            // read file into ArrayList inputPack
//            while (scanner.hasNextInt()) {
//                int line = scanner.nextInt();
//                inputPack.add(line);
//            }

            if (inputPack.size() == 8 * players) {
                // set valid = True to break loop
                valid = true;
            } else {
                System.out.println("Invalid: File must contain " +
                        (8 * players) + " lines, " + inputPack.size() + " found instead! ");
                inputPack.clear();
            }
        }

        return inputPack;
    }

    // distributes cards to n players

    public ArrayList<player> distributePlayers (int players, ArrayList<player>player) {
        // distribute cards to players
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
        return decks;
    }
    }