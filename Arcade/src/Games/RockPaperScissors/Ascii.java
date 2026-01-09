package Games.RockPaperScissors;
/**
 * Ascii creates the string ascii and contains constructor Ascii and method toString
 *
 * @author Will Miller
 * @version 3.0
 */
public class Ascii
{
    // instance variables - replace the example below with your own

    String ascii;
    
    /**
     * takes in a string and returns nothing but sets the string ascii to the input
     */
    public Ascii(String ascii){
        this.ascii = ascii;
    }
    /**
     * takes in nothing but returns the string ascii
     */
    public String toString(){
        return this.ascii;
    }
}
