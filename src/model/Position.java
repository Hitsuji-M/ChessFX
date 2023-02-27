package model;

import java.util.regex.Pattern;

/**
 * Class Position to manage the position of the pieces
 * @author Hitsuji
 */
public class Position
{
	private int x;
	private int y;
	
	/**
	 * Default constructor of the class
	 */
	public Position()
	{
		this(0, 0);
	}
	
	/**
	 * Natural constructor of the class
	 * @param x Position x of the piece
	 * @param y Position y of the piece
	 * @throws IllegalArgumentException Error send if the x or y position are out of the chessboard bounds
	 */
	public Position(final int x, final int y)
	{
		if ((0 <= x && x <= 7) && (0 <= y && y <= 7)) {
			this.x = x;
			this.y = y;
			return;
		}
		throw new IllegalArgumentException(x + "," + y);
	}
	
	/**
	 * Constructor for the position using an expression like 'B4'
	 * @param expression The expression to parse 
	 */
	public Position(String expression)
	{
		if (expression == null || expression.length() != 2) {
			throw new IllegalArgumentException("The expression must me 'LetterNumber' -> 'B4'");
		}
		
		expression = expression.toUpperCase();
		
		if(!Pattern.matches("[A-H][1-8]", expression)) {
			throw new IllegalArgumentException("The expression is impossible" +
											   "the letter must be between A to F and the number 1 to 8");
		}
		
		char letter = expression.charAt(0);
		char number = expression.charAt(1);
		
		int abs = ((int) letter) - 65;
		int ord = Character.getNumericValue(number) - 1;
		
		this.x = abs;
		this.y = ord;
	}
	
	/**
	 * Getter for the x position
	 * @return the X position
	 */
	public int getX()
	{
		return this.x;
	}
	
	/**
	 * Getter for the y position
	 * @return the Y position
	 */
	public int getY()
	{
		return this.y;
	}
	
	/**
	 * Get the Manhattan distance between 2 positions
	 * @param pos The position to reach
	 * @return The Manhattan distance to the position to reach
	 */
	public int manhattanDistance(final Position pos)
	{
		return Math.abs(pos.x - this.x) + Math.abs(pos.y - this.y);
	}
	
	/**
	 * Change the abscissa of the position
	 * @param x The new abscissa
	 */
	public void setX(final int x)
	{
		if (0 <= x && x <= 7) {
			this.x = x;
			return;
		}
		throw new IllegalArgumentException("Impossible abscissa given : " + x);
	}
	
	/**
	 * Change the ordinate of the position
	 * @param y The new ordinate
	 */
	public void setY(final int y)
	{
		if (0 <= y && y <= 7) {
			this.y = y;
			return;
		}
		throw new IllegalArgumentException("Impossible ordinate given : " + y);
	}
	
	/**
	 * Check if the positions are on the same line 
	 * @param pos The position to check
	 * @return True if they are on the same line, else false
	 */
	public boolean isOnSameLine(final Position pos)
	{
		return this.y == pos.y;
	}
	
	/**
	 * Check if the positions are on the same column
	 * @param pos The position to check
	 * @return True if they are on the same column, else false
	 */
	public boolean isOnSameColumn(final Position pos)
	{
		return this.x == pos.x;
	}	
	
	/**
	 * Check if the positions are on the same diagonal
	 * @param pos The position to check
	 * @return True if they are on the same diagonal, else false
	 */
	public boolean isOnSameDiagonal(final Position pos)
	{
		return (Math.abs(this.x - pos.x)) == (Math.abs(this.y - pos.y));
	}
	
	@Override
	public boolean equals(final Object o)
	{
		if (this == o) {
			return true;
		}
		
		if (o != null && o.getClass() == this.getClass()) {
			Position pos = (Position) o;
			return this.x == pos.x && this.y == pos.y;
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		return "(" + (this.x + 1) + "," + (this.y + 1) + ")";
	}
}
