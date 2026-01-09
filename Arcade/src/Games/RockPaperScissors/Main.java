package Games.RockPaperScissors;
/**
 * Main contains method main
 *
 * @author Will Miller
 * @version 3.0
 */
public class Main
{
    /**
     * main takes in String[] args and returns nothing but makes an object game for RockPaperScissors
     * and starts the game
     */
    public static void main(String[] args){
        /*Ascii art = new Ascii("(^_^.)");
        System.out.println(art);
        
        Rock rock = new Rock();
        System.out.println(rock);
        
        Paper paper = new Paper();
        System.out.println(paper);
        
        Scissors scissors = new Scissors();
        System.out.println(scissors);
        */
        RockPaperScissors game = new RockPaperScissors();
        game.play();
        //System.out.println(ascii);
    }
}


 

 

