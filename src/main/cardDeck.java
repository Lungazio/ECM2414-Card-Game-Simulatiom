/**
 * cardDeck class:
 * Holds all data related to card decks,
 *
 * "each player picks a card from the top
 * of the deck to their left, discards one
 * to the bottom of the deck to their right
 *
 * Example scenarios:
 * player 1 picks from deck 1,
 * player 1 disposes to deck 2
 *
 * player 4 picks from deck 4
 * player 4 disposes to deck 1
 *
 * Includes get/set methods
 * Holds data of decks
 *
 * @author Daphne Yap & Julian Lung
 * @version 1.0
 *
 */
public class cardDeck {
    private int playerId;
    private int deckId;
    public int[] deck;

    public void dispose(int deckId) {
        // dispose to the bottom of
        // deckId + 1 or if last player,
        // dispose to id of first player
    }

    public int[] getCardDeck (int deckId) {
        return this.deck;
    }

}