package Games.RockPaperScissors;
/**
 * Scissors extends Hand and contains methods whoWins and Scissors
 *
* @author Will Miller
 * @version 3.0
 */
public class Scissors extends Hand
{
    /**
     * whoWins takes in a hand and returns a string saying who wins depending on the matchup,
     * or that 'Something went wrong." if the input isn't rock, paper, or scissors.
     */
    public String whoWins(Hand hand){
        if ((hand.name).equals("paper")){
           return "Player 1 wins!";
        }
        else if ((hand.name).equals("rock")){
            return "Player 2 wins!";
        }
        else if ((hand.name).equals("scissors")){
            return "It's a tie!";
        }
        else{
            return "Something went wrong.";
        }
    }
    
    /**
     * Scissors takes in nothing and returns nothing but puts scissors and the scissors art into class Hand.
     */
    public Scissors(){
        super("scissors", 
          "    .-." + "\n"
        + "    | |    / )" + "\n"
        + "    | |   / /" + "\n"
        + "    | |  / /" + "\n"
        + " _.-| |_/ /" + "\n"
        + "; \\( \\   /" + "\n"
        + "|\\_)\\ \\  |" + "\n"
        + "|    ) \\ |" + "\n"
        + "|   (    /" + "\n"
        + " \\______/" + "\n");
    }  
}