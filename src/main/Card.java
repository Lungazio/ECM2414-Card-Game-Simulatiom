import java.io.File;
import java.io.FileNotFoundException;
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

public class Card {
    private ArrayList<Integer> inputPack = new ArrayList<>();

    public ArrayList<Integer> getInputPack(int players) throws FileNotFoundException {
        Scanner reader = new Scanner(System.in);
        boolean valid = false;
        Path file_path = null;
        while (valid == false) {
            System.out.print("Enter input pack location: ");
            file_path = Paths.get(reader.nextLine());

            // file exists, read file and break while loop
            if (Files.exists(file_path)) {
                // initialize scanner
                Scanner scanner = new Scanner(new File(String.valueOf(file_path)));

                /** CHECK IF INPUT PACK LINES = 8N **/

                // read file into ArrayList inputPack
                while (scanner.hasNextInt()) {
                    int line = scanner.nextInt();
                    inputPack.add(line);
                }

                if (inputPack.size() == 8 * players) {
                    // set valid = True to break loop
                    valid = true;
                }
            }
        }

        // close loop
        reader.close();



        return inputPack;
    }


    // distributes cards to n players
    public void distributeCards (int n) {
        // distribute cards to players

        // distribute cards to decks
    }
}