package Games.TicTacToe;
import Games.AGame;

import java.util.Scanner;
import java.util.Arrays;
/**
 * TicTacToeGame
 *
 * @author Prof Prairie
 * @author Will Miller
 * @version 1.1
 */
public class TicTacToe extends AGame
{
    char player1;
    char player2;
    Board b;
    Board copy;
    int SIZE = 3;
    boolean validMove = false;

    Scanner scanner = new Scanner(System.in);
    
    public TicTacToe(char player1, char player2){
        super.name = "Tic-Tac-Toe";
        this.player1 = player1;
        this.player2 = player2;
    }

    public void play() {
        boolean end = false;
        while (!end) {
            System.out.println("\f");
            System.out.println("Welcome to tic-toe-toe!");
            System.out.println("What length of grid do you want (between 3 and 9)?");
            for (int i = 0; i == 0; ) {
                try {
                    SIZE = Integer.parseInt(scanner.nextLine());
                    if (SIZE < 3 || SIZE > 9) {
                        System.out.println("You did not enter a valid number.");
                        System.out.println("Please enter a number between 3 and 9.");
                    } else {
                        i = 1;
                    }
                } catch (Exception NumberFormatException) {
                    System.out.println("You did not enter a number");
                    System.out.println("Please enter a number between 3 and 9.");
                }
            }
            this.b = new Board(SIZE);
            this.copy = replica(b);

            int numPlayers = -1;
            System.out.println("1 or 2 players?");
            for (int i = 0; i == 0; ) {
                try {
                    numPlayers = Integer.parseInt(scanner.nextLine());
                    if (numPlayers != 1 && numPlayers != 2) {
                        System.out.println("You did not enter a valid number.");
                        System.out.println("Please enter 1 (for one player) or 2 (for 2 players).");
                    } else {
                        i = 1;
                    }
                } catch (Exception NumberFormatException) {
                    System.out.println("You did not enter a number");
                    System.out.println("Please enter 1 (for 1 player) or 2 (for 2 players).");
                }
            }
            boolean won = false;
            if (numPlayers == 2) {
                char currentTurn = player1;
                for (int i = 0; i < SIZE * SIZE; i++) {
                    System.out.println(this.b);
                    validMove = false;
                    while (!validMove) {
                        Move move = getMove(currentTurn);
                        try {
                            won = this.b.makeMove(move);
                            validMove = true;
                        } catch (ThinkingOutsideTheBoxException e) {
                            System.out.println("Sorry, that move's not valid.  Please try again.");
                        }
                    }
                    if (won) {
                        System.out.println(this.b);
                        System.out.println("Congrats!! " + currentTurn + " wins!");
                        System.out.println("Enter q to exit to the main menu or any other key to continue Tic-Tac-Toe!");
                        if (scanner.nextLine() == "q") {
                            System.out.print("\f");
                            end = true;
                        }
                        break;
                    }
                    currentTurn = switchTurn(currentTurn);
                }
                System.out.println(this.b);
                System.out.println("It's a tie!");
            }
            else {
                botPlay();
            }

            System.out.println("Enter q to exit to the main menu or any other key to continue Tic-Tac-Toe!");
            if (scanner.nextLine().equals("q")) {
                System.out.print("\f");
                end = true;
            }
        }

    }
    
    private void botPlay(){
        int turns = 0;
        double coin = Math.random();
        if (coin < 0.5){
            System.out.println("You are " + player1 + " and move first.");
            for(int i=0;i<(SIZE*SIZE+1)/2;i++) {
                boolean won = false;
                System.out.println(this.b);
                validMove = false;
                while (!validMove) {
                    Move move = getMove(player1);
                    try {
                        won = this.b.makeMove(move);
                        validMove = true;
                        turns++;
                    } catch (ThinkingOutsideTheBoxException e) {
                        System.out.println("Sorry, that move's not valid.  Please try again.");
                    }
                }

                System.out.println(this.b);
                if (won) {
                    System.out.println("Congrats!! " + player1 + " wins!");
                    return;
                }
                else if (turns >= SIZE * SIZE) {
                    System.out.println("It's a tie!");
                    return;
                }

                validMove = false;
                while (!validMove) {
                    /*Bot bot = new Bot();
                    Move rando = bot.choose(SIZE);
                    Move botMove = new Move(player2,rando.row, rando.column);*/
                    Move botMove = botBrain(player2);
                    try {
                        won = this.b.makeMove(botMove);
                        validMove = true;
                        turns++;
                    } catch (ThinkingOutsideTheBoxException e) {

                    }
                }
                System.out.println(this.b);
                if (won) {
                    System.out.println(player2 + " wins!");
                    return;
                }
                else if (turns >= SIZE*SIZE){
                    System.out.println("It's a tie!");
                    return;
                }
            }
        }
        else{
            validMove = false;
            System.out.println("You are " + player2 + " and move second.");
            for(int i=0;i<(SIZE*SIZE+1)/2;i++){
                System.out.println(this.b);
                boolean won = false;
                validMove = false;
                while (!validMove){
                    /*Bot bot = new Bot();
                    Move rando = bot.choose(SIZE);
                    Move botMove = new Move(player1,rando.row, rando.column);*/
                    Move botMove = botBrain(player1);
                    try{
                        won = this.b.makeMove(botMove);
                        validMove = true;
                        turns++;
                    }catch(ThinkingOutsideTheBoxException e){

                    }
                }
                System.out.println(this.b);
                if(won){
                    System.out.println(player1 + " wins!");
                    return;
                }
                else if (turns >= SIZE*SIZE){
                    System.out.println("It's a tie!");
                    return;
                }

                validMove = false;
                while(!validMove){
                    Move move = getMove(player2);
                    try{
                        won = this.b.makeMove(move);
                        validMove = true;
                        turns++;
                    }catch(ThinkingOutsideTheBoxException e){
                        System.out.println("Sorry, that move's not valid.  Please try again.");

                    }
                }
                if(won){
                    System.out.println(this.b);
                    System.out.println("Congrats!! " + player2 + " wins!");
                    return;
                }
                else if (turns >= SIZE*SIZE){
                    System.out.println("It's a tie!");
                    return;
                }
            }
        }
    }
    
    private char switchTurn(char turn){
        if(turn == this.player1){
            return this.player2;
        }
        else return this.player1;
    }

    public Move getMove(char c){
        System.out.println(c + "'s turn. Please enter row and column separated by a comma (e.g. 0,0)");

        while(true){
            String answer = scanner.nextLine();
            String[] tokens = answer.split(",");
            Arrays.toString(tokens);
            if(tokens.length != 2){
                System.out.println("Invalid answer.   Please enter a move in the form of \"row,col\"(e.g. 1,2)");
                continue;
            } 

            try{
                int row = Integer.parseInt(tokens[0]);
                int column = Integer.parseInt(tokens[1]);
                return new Move(c,row,column);
            }catch(Exception e){
                System.out.println("Please enter two numbers split by a comma. (e.g. 1,2)");
                continue;
            }
        }
    }
    
    public Move botBrain(char c){
        for(int i=0;i<SIZE;i++){
            for(int j=0;j<SIZE;j++){
                copy = replica(b);
                Move win = new Move(c, i, j);
                if (b.isValidMove(win)){
                    copy.space[i][j] = c;
                    if (copy.isWinner(c)){
                        return win;
                    }
                } 
            }
        }
        
        char other;
        if (c == player2){
            other = player1;
        }
        else{
            other = player2;
        }
        for(int i=0;i<SIZE;i++){
            for(int j=0;j<SIZE;j++){
                copy = replica(b);
                Move blocked = new Move(other, i, j);
                if (b.isValidMove(blocked)){
                    copy.space[i][j] = other;
                    if (copy.isWinner(other)){
                        Move block = new Move(c, i, j);
                        return block;
                    }
                }
            }
        }
        
        Bot bot = new Bot();
        Move rando = bot.choose(SIZE);
        Move botMove = new Move(c, rando.row, rando.column);
        return botMove;
    }
    
    public Board replica(Board board){
        Board copy = new Board(SIZE);
        for(int i=0;i<SIZE;i++){
            for(int j=0;j<SIZE;j++){
            copy.space[i][j] = board.space[i][j];
            }
        }
        return copy;
    }

    public int compareTo(AGame o) {
        return 0;
    }
}