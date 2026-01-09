package Games.TicTacToe;
/**
 * Main class for TicTacToe, runs the game.
 *
 * @author Prof Prairie
 * @version 1.1
 */
public class Main
{
    public static void main(String[] args){ 
        TicTacToe game;
        game = new TicTacToe('X', 'O');
        game.play();
        

    }
}
