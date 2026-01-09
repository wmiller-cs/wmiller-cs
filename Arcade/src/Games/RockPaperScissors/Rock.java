package Games.RockPaperScissors;
/**
 * Rock extends Hand and contains method whoWins and method Rock
 *
 * @author Will Miller
 * @version 3.0
 */
public class Rock extends Hand
{
    /**
     * whoWins takes in a hand and returns a string saying who wins depending on the matchup,
     * or that 'Something went wrong." if the input isn't rock, paper, or scissors.
     */
    public String whoWins(Hand hand){
        if ((hand.name).equals("scissors")){
           return "Player 1 wins!";
        }
        else if ((hand.name).equals("paper")){
            return "Player 2 wins!";
        }
        else if ((hand.name).equals("rock")){
            return "It's a tie!";
        }
        else{
            return "Something went wrong.";
        }
    }
    
    /**
     * Rock takes in nothing and returns nothing but puts rock and the rock art into class Hand.
     */
    public Rock(){
        super("rock", 
        " _.-.-.-." + "\n"
        + ";_|_|_|_|_" + "\n"
        + "|_|_|\\__  \\" + "\n"
        + "|    . '  |" + "\n"
        + "|   (    /" + "\n"
        + " \\______/" + "\n");
    }
}