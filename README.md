

# Multi-Threaded Card Game
ECM2414 Paired Programming Continuous Assessment.

### About the game:
An **n** player game where each user draws and discards simultaneously until one of the players have a winning hand. A winning hand is where a player has 4 cards of their preferred denomination. Player 1's preferred denomination is 1, Player 2's preferred denomination is 2 etc.

At the end of the game, the program generates output files for each player, containing a complete log of the player's actions. The deck output file contains the deck contents of each player at the end of the game.


## Running the JAR
First, we have to make sure Java is installed. Run `java -version` to display installed java version. If Java is not installed, it can be done from [this link here](https://www.java.com/en/download/help/download_options.html)

---
1. Open command prompt and navigate to the directory where cards.jar is stored.
2. Run the jar file with the following command.


``` cmd
java -jar cards.jar
``` 

3. Enter number of players.
4. Enter the file location of your input pack. e.g. src/inputpack.txt
The game should start running after taking valid inputs for both number of players and input pack location.

## Running Tests
Also in command prompt, make sure that the current working directory is in ECM2414. For example, typing `cd` in Command Prompt will give: 
C:\Users\user1\Downloads\ECM2414, containing the bin, lib and src folders.

Then to run the test suite, run the following command.

``` cmd 
java -cp ".\bin\Testing;.\lib\junit-4.13.1.jar;.\lib\hamcrest-core-1.3.jar" Testing.TestRunner
```

## Authors

- Julian Lung 
- Daphne Yap

