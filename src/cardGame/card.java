package cardGame;

/**
 * card class:
 * To store input pack values as cards.
 *
 * @author Daphne Yap & Julian Lung
 * @version 5.0
 *
 */

public class card {
    public int value;



    card (int cardValue){
        this.value = cardValue;
    }

    /**
     * Returns the value of the current card
     *
     * @return int value of the card
     *
     */
    public int getValue() {
        return value;
    }

}
