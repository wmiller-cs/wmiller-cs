package Games.RockPaperScissors;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * LogicTests contains tests for every matchup to see if they give the right outcome
 * incorrectTest also tests an incorrect input to see if it properly returns null
 *
 * @author  Will Miller
 * @version 3.0
 */
public class LogicTests
{
    @Test

    public void rockscissorsTest(){

        //assertEquals(expected, actual);

        Hand rock = new Rock();

        Hand scissors = new Scissors();

        assertEquals("Player 1 wins!",rock.whoWins(scissors));

    }

 

    @Test

    public void rockpaperTest(){

        //assertEquals(expected, actual);

        Hand rock = new Rock();

        Hand paper = new Paper();

        assertEquals("Player 2 wins!",rock.whoWins(paper));

    }

 

    @Test

    public void rockrockTest(){

        //assertEquals(expected, actual);

        Hand rock = new Rock();

        Hand roll = new Rock();

        assertEquals("It's a tie!",rock.whoWins(roll));

    }

    
    
    @Test

    public void paperscissorsTest(){

        //assertEquals(expected, actual);

        Hand paper = new Paper();

        Hand scissors = new Scissors();

        assertEquals("Player 2 wins!",paper.whoWins(scissors));

    }

 

    @Test

    public void paperpaperTest(){

        //assertEquals(expected, actual);

        Hand paper = new Paper();

        Hand roll = new Paper();

        assertEquals("It's a tie!",paper.whoWins(paper));

    }

 

    @Test

    public void paperrockTest(){

        //assertEquals(expected, actual);

        Hand paper = new Paper();

        Hand rock = new Rock();

        assertEquals("Player 1 wins!",paper.whoWins(rock));

    }
    
    @Test

    public void scissorsscissorsTest(){

        //assertEquals(expected, actual);

        Hand scissors = new Scissors();

        Hand roll = new Scissors();

        assertEquals("It's a tie!",scissors.whoWins(roll));

    }

 

    @Test

    public void scissorspaperTest(){

        //assertEquals(expected, actual);

        Hand scissors = new Scissors();

        Hand paper = new Paper();

        assertEquals("Player 1 wins!",scissors.whoWins(paper));

    }

 

    @Test

    public void scissorsrockTest(){

        //assertEquals(expected, actual);

        Hand scissors = new Scissors();

        Hand rock = new Rock();

        assertEquals("Player 2 wins!",scissors.whoWins(rock));

    }
    
    @Test

    public void incorrectTest(){
        String wrong = "stick";
        RockPaperScissors bad = new RockPaperScissors();
        Hand result = bad.handCreation(wrong);
        assertEquals(null, result);
    }
}
