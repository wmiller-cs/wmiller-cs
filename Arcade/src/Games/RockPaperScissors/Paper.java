package Games.RockPaperScissors;
/**
 * Paper extends Hand and contains method whoWins and method Paper
 *
 * @author Will Miller
 * @version 3.0
 */
public class Paper extends Hand
{
    /**
     * whoWins takes in a hand and returns a string saying who wins depending on the matchup,
     * or that 'Something went wrong." if the input isn't rock, paper, or scissors.
     */
    public String whoWins(Hand hand){
        if ((hand.name).equals("rock")){
           return "Player 1 wins!";
        }
        else if ((hand.name).equals("scissors")){
            return "Player 2 wins!";
        }
        else if ((hand.name).equals("paper")){
            return "It's a tie!";
        }
        else{
            return "Something went wrong.";
        }
    }
    
    /**
     * Paper takes in nothing and returns nothing but puts paper and the paper art into class Hand.
     */
    public Paper(){
        super("paper", 
        "       _______" + "\n"
        + "  ____(____     \\" + "\n"
        + " (________       |" + "\n"
        + "(_________       |" + "\n"
        + " (________       |" + "\n"
        + "  (___________  /" + "\n");
    }  
}
