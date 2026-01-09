package Games.TicTacToe;
/**
 * Bot .
 *
 * @author Will Miller
 * @version 1.1
 */
public class Bot
{
    /**
     * method choose takes in int size and returns a random move, all of which have equal likelihood
     */
    public static Move choose(int size){
        char symbol = 'O';
        int row = (int)(Math.random()*size);
        int column = (int)(Math.random()*size);
        Move bot = new Move(symbol, row, column);
        return bot;
    }
}
