package Games.TicTacToe;
/**
 * ThinkingOutsideTheBoxException.
 *
 * @author Will Miller
 * @version 1.1
 */
public class ThinkingOutsideTheBoxException extends IllegalArgumentException
{
    ThinkingOutsideTheBoxException(){}

    ThinkingOutsideTheBoxException(String msg){
        super(msg);
    }
}
