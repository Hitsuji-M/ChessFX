package utils;

/**
 * Exception when a player is doing a wrong move
 * @author Hitsuji
 *
 */
public class ChessMoveException extends Exception
{
	public ChessMoveException()
	{
		super("This is an illegal move");
	}
	
	public ChessMoveException(final String s)
	{
		super(s);
	}
	
	public ChessMoveException(final String s, final String start, final String end)
	{
		super(s + " : " + start + " -> " + end);
	}
}
