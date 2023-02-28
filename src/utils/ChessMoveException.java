package utils;

/**
 * Exception when a player is doing a wrong move
 * @author Hitsuji
 *
 */
public class ChessMoveException extends Exception
{
	/**
	 * Default constructor 
	 */
	public ChessMoveException()
	{
		super("This is an illegal move");
	}
	
	/**
	 * Constructor for the exception class
	 * @param s Custom string for esception message
	 */
	public ChessMoveException(final String s)
	{
		super(s);
	}
	
	/**
	 * Constructor for the exception class
	 * @param s Custom string for esception message
	 * @param start Starting position string
	 * @param end Ending position string
	 */
	public ChessMoveException(final String s, final String start, final String end)
	{
		super(s + " : " + start + " -> " + end);
	}
}
