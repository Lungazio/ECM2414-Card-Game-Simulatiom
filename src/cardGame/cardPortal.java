package cardGame;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.util.*;

public class cardPortal{
    public ArrayList<player> players = new ArrayList<>();
    public ArrayList<cardDeck> decks = new ArrayList<>();
    card card1 = new card();


    public void getPlayersInputPack() throws Exception {
        System.out.print("Enter number of players: ");
        Scanner scanner = new Scanner(System.in);

        Integer numOfPlayers = scanner.nextInt();
        if (numOfPlayers < 2) throw new IllegalArgumentException("There should be at least 2 Players.");
        else{
            //create player
            for (int i = 0; i < numOfPlayers; i++) {
                player temp = new player(i, new ArrayList<>());
                players.add(temp);
            }
            card1.getInputPack(numOfPlayers);
        }
    }
}

