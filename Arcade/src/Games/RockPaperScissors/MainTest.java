package Games.RockPaperScissors;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * MainTest contains tests comparing the arts for rock, paper, and scissors to
 * the expected values
 *
 * @author Will Miller
 * @version 3.0
 */
public class MainTest
{
    @Test

    public void rockTest(){

        //assertEquals(expected, actual);

        Hand rock = new Rock();

        String expected = " _.-.-.-.\n;_|_|_|_|_\n|_|_|\\__  \\\n|    . '  |\n|   (    /\n \\______/\n"; 

        assertEquals(expected, rock.ascii);

    }

 

    @Test

    public void paperTest(){

        //assertEquals(expected, actual);

        Hand paper = new Paper();

        String expected = "       _______\n  ____(____     \\\n (________       |\n(_________       |\n (________       |\n  (___________  /\n";

        assertEquals(expected, paper.ascii);

    }

 

    @Test

    public void scissorsTest(){

        //assertEquals(expected, actual);

        Hand scissors = new Scissors();

        String expected = "    .-.\n    | |    / )\n    | |   / /\n    | |  / /\n _.-| |_/ /\n; \\( \\   /\n|\\_)\\ \\  |\n|    ) \\ |\n|   (    /\n \\______/\n";

        assertEquals(expected, scissors.ascii);

    }

 
}
