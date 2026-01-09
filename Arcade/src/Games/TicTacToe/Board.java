package Games.TicTacToe;
/**
 * Board for playing tic-tac-toe
 *
 * @author Prof Prairie
 * @author Will Miller
 * @version 1.1
 */
public class Board
{
    char[][] space;
    private int size;
    
    /**
     * Constructor initializes all spots to underscore.
     * 
     * @param size: the dimensions of the square board.
     */
    
    Board(int size){
        this.size = size;
        space = new char[size][size];
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                space[i][j] = '_';
            }
        }
        toString();
    }

    /**
     * toString method prints the board as a square
     * @return String representing the printed board
     */
    public String toString(){
        String ans = "";
        for(int i = -1; i<size; i++){
            if (i == -1){
                for(int j = -1; j<size; j++){
                    if (j == -1){
                        ans += "  ";
                    }
                    else{
                        ans += j + " ";
                    }
                }
            }
            else{
                for(int j = -1; j<size; j++){
                    if (j == -1){
                        ans += i + " ";
                    }
                    else{
                        ans += space[i][j] + " ";
                    }
                }
            }
            ans += "\n";
        }
        return ans;
    }

    /**
     * Checks to see if the specified move is valid on the current board.
     * 
     * @param move: represents the potential move.
     */
    public boolean isValidMove(Move move){
        try{
            if (space[move.row][move.column] == '_'){
                return true;
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            return false;
        }
    }

    /**
     * Checks to see if a player has won the game.
     * 
     * @param c: the player's character.
     * 
     * @return boolean if the specified player has won.
     */
    public boolean isWinner(char c){
        int score = 0;
        
        //horizontal
        for (int i = 0; i<size; i++){
            score = 0;
            for (int j = 0; j<size; j++){
                if (space[i][j] == c){
                    score++;
                }
            }
            if (score >= size){
                return true;
            }
        }
        
        //vertical
        for (int i = 0; i<size; i++){
            score = 0;
            for (int j = 0; j<size; j++){
                if (space[j][i] == c){
                    score++;
                }
            }
            if (score >= size){
                return true;
            }
        }
        
        
        
        //diagonal forward
        score = 0;
        for (int i = 0; i<size; i++){   
            if (space[i][i] == c){
                score++;
            }
            if (score >= size){
                return true;
            }
        }
        
        //diagonal backward
        score = 0;
        for (int i = 0; i<size; i++){   
            if (space[size-1-i][i] == c){
                score++;
            }
            if (score >= size){
                return true;
            }
        }
        
        return false;
    }

    /**
     * Places the move on the board.
     * @throws ThinkingOutsideTheBoxException for invalid move
     * @param move the move to place on the board
     * @return boolean representing if the move won the game.
     */
    public boolean makeMove(Move move) throws ThinkingOutsideTheBoxException{
        if (isValidMove(move)){
            space[move.row][move.column] = move.symbol;
            return isWinner(move.symbol);
        }
        else{
            throw new ThinkingOutsideTheBoxException();
        }
    }

    public boolean isFull(){
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                if (space[i][j] != '_'){
                    return false;
                }

            }
        }
        return true;

    }
}
