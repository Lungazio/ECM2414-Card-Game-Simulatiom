import cardGame.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class cardGame {

    public static void main(String[] args) throws Exception {
        cardPortal portal1 = new cardPortal();

        // portal1.getNumPlayers();
        portal1.getPlayersInputPack();
        portal1.testDiscards();
        portal1.testThreads();
    }
}
