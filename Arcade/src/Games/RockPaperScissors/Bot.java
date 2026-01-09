package Games.RockPaperScissors;
/**
 * Bot contains the method choose
 *
 * @author Will Miller
 * @version 3.0
 */
public class Bot
{  
    /**
     * method choose takes in nothing and randomly returns a Hand, either rock, paper, or scissors,
     * each with a 1/3 chance
     */
    public static Hand choose(){
        double choice = Math.random();
        if (choice < 1.0/3.0){
            Rock rock = new Rock();
            return rock;
        }
        else if (choice >= 1.0/3.0 && choice < 2.0/3.0){
            Paper paper = new Paper();
            return paper;
        }
        else{
            Scissors scissors = new Scissors();
            return scissors;
        }
    }
}
