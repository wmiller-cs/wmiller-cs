package Games.TicTacToe;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class TicTacTest.
 *
 * @author Prof Prairie
 * @version 1.1
 */
public class TicTacTest
{
    /**
     * Default constructor for test class TicTacTest
     */
    public TicTacTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
    }

    @Test
    public void BoardToStringTest(){
        Board b = new Board(3);
        String actual = b.toString();
        String expect = 
            "  0 1 2 \n" + //<- two spaces at the beginning,one at the end
            "0 _ _ _ \n"+ //<- space after index num, and one at the end (no special case for last char)
            "1 _ _ _ \n" +
            "2 _ _ _ \n"; //<- still a new line at the end.

        assertEquals(expect, actual);

        Board small = new Board(2);
        actual = small.toString();
        expect = 
        "  0 1 \n" + //<- two spaces at the beginning, one at the end
        "0 _ _ \n"+ //<- space after index num, and one at the end (no special case for last char)
        "1 _ _ \n"; //<- still a new line at the end.

        assertEquals(expect,actual);

        Board big = new Board(4);
        actual = big.toString();
        expect = 
            "  0 1 2 3 \n" + //<- two spaces at the beginning, one at the end
            "0 _ _ _ _ \n"+ //<- space after index num, and one at the end (no special case for last char)
            "1 _ _ _ _ \n" +
            "2 _ _ _ _ \n" +
            "3 _ _ _ _ \n"; //<- still a new line at the end.

        assertEquals(expect, actual);

    }
    @Test
    public void isValidMoveTest(){
        Board b = new Board(3);
        Move m = new Move('X',0,0);
        Move n = new Move('X',-2,2);
        Move o = new Move('X',1,-1);
        Move p = new Move('X',3,3);

        assertEquals(true, b.isValidMove(m));
        assertEquals(false,b.isValidMove(n));
        assertEquals(false,b.isValidMove(o));
        assertEquals(false,b.isValidMove(p));

        Board big = new Board(5);
        Move bigMove = new Move('X',4,4);
        assertEquals(true,big.isValidMove(bigMove));

    }

    @Test
    public void isWinnerTest(){
        //first test makeMove
        Board b = new Board(3);
        Move m1 = new Move('X',0,0);
        Move m2 = new Move('O',1,1);
        Move m3 = new Move('X',2,2);
        b.makeMove(m1);
        b.makeMove(m2);
        b.makeMove(m3);
        String actual = b.toString();
        String expect = 
            "  0 1 2 \n" + //<- two spaces at the beginning, none at the end
            "0 X _ _ \n"+ //<- space after index num, and one at the end (no special case for last char)
            "1 _ O _ \n" +
            "2 _ _ X \n"; //<- still a new line at the end.

        assertEquals(expect, actual, "Check your toString Method, and the board as shown in the tests.");

        //next test isWinner
        assertEquals(false,b.isWinner('O'),"O is a winner but shouldn't be");
        assertEquals(false,b.isWinner('X'),"X is a winner but shouldn't be");

        //rows
        Board row0Winner = makeRowWinner(3, 'X',0);
        Board row1Winner = makeRowWinner(3, 'X',1);
        Board row2Winner = makeRowWinner(3, 'X',2);
        assertEquals(true, row0Winner.isWinner('X'), "The top row has winner that was not found");
        assertEquals(true, row1Winner.isWinner('X'), "The middle row has winner that was not found");
        assertEquals(true, row2Winner.isWinner('X'), "The bottom row has winner that was not found");

        //columns
        Board col0Winner = makeColWinner(3, 'X',0);
        Board col1Winner = makeColWinner(3, 'X',1);
        Board col2Winner = makeColWinner(3, 'X',2);
        assertEquals(true,col0Winner.isWinner('X'),"The first column has winner that was not found");
        assertEquals(true,col1Winner.isWinner('X'),"The middle column has winner that was not found");
        assertEquals(true,col2Winner.isWinner('X'),"The last column has winner that was not found");

        //diagonals
        Board diagForward = makeDiagWinner(3, 'X',true);
        Board diagBackward = makeDiagWinner(3, 'X',false);
        assertEquals(true, diagForward.isWinner('X'), "Forward diagonal was not found");
        assertEquals(true, diagBackward.isWinner('X'), "backward diagonal was not found");

        //some other tests
        assert(!row2Winner.isWinner('O'));
        assert(!col2Winner.isWinner('O'));

        Board c = new Board(3);
        c.makeMove(new Move('X',0,0));
        assert(!c.isWinner('X'));
        assert(!c.isWinner('O'));

        //Other sizes
        Board small = makeRowWinner(2,'X',0);
        Board big = makeRowWinner(5,'X',0);
        assertEquals(true,small.isWinner('X'), "Can't hardcode sizes");
        assertEquals(true,big.isWinner('X'), "Can't hardcode sizes");
    }

    /**
     * Helper factory methods
     */
    Board makeRowWinner(int size, char c, int row){
        Board b = new Board(size);
        for(int i=0;i<size;i++){
            b.makeMove(new Move(c,row,i));
        }        
        return b;
    }

    Board makeColWinner(int size, char c, int col){
        Board b = new Board(size);
        for(int i=0;i<size;i++){
            b.makeMove(new Move(c,i,col));
        }
        return b;
    }

    Board makeDiagWinner(int size, char c, boolean forward){
        Board b = new Board(size);
        if(forward){
            for(int i=0;i<size;i++){
                b.makeMove(new Move(c,i,i));
            }
        } else {
            for(int i=0,j=size-1;i<size;i++,j--){
                b.makeMove(new Move(c,i,j));
            }

        }

        return b;

    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }
}
