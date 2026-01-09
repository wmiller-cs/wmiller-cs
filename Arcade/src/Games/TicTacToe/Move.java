package Games.TicTacToe;
/**
 * An ordered pair of coordinates on a tic-tac-toe board
 *
 * @author Prof Prairie
 * @version 1.1
 */
public class Move
{
    public int row;
    public int column;
    public char symbol;
    public Move(char symbol, int row, int column){
        this.symbol = symbol;
        this.row = row;
        this.column = column;
    }
    
    public String toString(){
        return symbol + "," + row + "," + column;
    }
}
