package Games.RockPaperScissors;
import Games.AGame;
import java.util.Scanner;

/**
 * RockPaperScissors contains methods play and handCreation
 *
 * @author Will Miller
 * @version 3.0
 */
public class RockPaperScissors extends AGame
{
    public RockPaperScissors(){
        super.name = "Rock Paper Scissors";
    }
    /**
     * play prompts the user to enter in 1 or 2 players,
     * then asks the first player for their choice of weapon (rock, paper, or scissors)
     * If two players are playing, it asks the second player for their choice,
     * otherwise, it gets a random selection from Bot.choose()
     * and matches up the two inputs and tells the user who wins
     * throughout all of this, it keeps asking after every invalid input until a valid input is entered.
     */
    public void play(){
        Scanner scanner = new Scanner(System.in);
        Task: while(true){
            System.out.print("\f");
            System.out.println("Welcome to Will’s Rock Paper Scissors Death Tournament to the DEATH!!!");
            
            int numPlayers = -1;
            System.out.println("1 or 2 players?");
            
            for (int i = 0; i == 0;){
                try {
                    numPlayers = Integer.parseInt(scanner.nextLine());
                    if (numPlayers != 1 && numPlayers != 2){
                        System.out.println("You did not enter a valid number.");
                        System.out.println("Please enter 1 (for one player) or 2 (for 2 players).");
                    }
                    else{
                        i = 1;
                    }
                }
                catch (Exception NumberFormatException){
                    System.out.println("You did not enter a number");
                    System.out.println("Please enter 1 (for 1 player) or 2 (for 2 players).");
                }
            }
            
            Hand player1hand = new Rock();
            System.out.println("Player 1, pick your weapon!");
            for (int i = 0; i == 0;){
                String response = scanner.nextLine();
                if (response.equals("rock") || response.equals("paper") || response.equals("scissors")){
                    player1hand = handCreation(response);
                    i = 1;
                }
                else{
                    System.out.println("Invalid input.");
                    System.out.println("Player 1, pick rock, paper, or scissors!");
                }
            }
            System.out.print("\f");
            Hand player2hand = new Rock();
            
            if (numPlayers == 2){
                System.out.println("Player 2, pick your weapon!");
                for (int i = 0; i == 0;){
                    String response = scanner.nextLine();
                    if (response.equals("rock") || response.equals("paper") || response.equals("scissors")){
                        player2hand = handCreation(response);
                        i = 1;
                    }
                    else{
                        System.out.println("Invalid input.");
                        System.out.println("Player 2, pick rock, paper, or scissors!");
                    }
                }
            }
            else{
                player2hand = Bot.choose();
            }
            System.out.print("\f");
            
            String result = player1hand.whoWins(player2hand);
            System.out.println(player1hand);
            System.out.println(player2hand);
            System.out.println(result);
            System.out.print("\n");
            System.out.println("Enter q to exit to the main menu or any other key to continue Rock Paper Scissors!");
            String response = scanner.nextLine();
            if (response.equals("q")){
                System.out.print("\f");
                break Task;
            }
        }
    }
    /**
     * handCreation takes in a string and returns a Hand
     * it returns a Rock, Paper, or Scissors for the inputs rock, paper, or scissors respectively,
     * or returns null if the input isn't any of those
     */
    public Hand handCreation(String response){
        response = response.toLowerCase();
            if (response.equals("scissors")){
                Scissors player1hand = new Scissors();
                return player1hand;
            }
            else if (response.equals("rock")){
                Rock player1hand = new Rock();
                return player1hand;
            }
            else if (response.equals("paper")){
                Paper player1hand = new Paper();
                return player1hand;
            }
            else{
                return null;
            }
    }

    public int compareTo(AGame o) {
        return 0;
    }
}
