package Games.RockPaperScissors;
/**
 * Hand extends Ascii and creates String name, and contains methods Hand, whoWins, and toString.
 *
 * @author Will Miller
 * @version 3.0
 */
public class Hand extends Ascii
{
    String name;
    /**
     * Hand takes in a String for the name and another string for the art and sends the art to Ascii,
     * and sets the string name to the inputed name
     */
    public Hand(String name, String ascii){
        super(ascii);
        this.name = name;
    }
    
    /**
     * whoWins takes in a hand and returns a string, saying "Play the game to win!"
     */
    public String whoWins(Hand hand){
        return "Play the game to win!";
    }
    
    /**
     * toString takes in nothing and returns a string displaying the name, a line break, and the art
     */
    public String toString(){
        return name + "\n" + ascii;
    }
}
