package model;

import java.util.ArrayList;

import utils.ColorG;

/**
 * Class to manage the players
 * @author Hitsuji
 */
public class Player
{
	private String name;
	private ColorG color;
	private Position kingPos;
	private boolean canDoLittleCastling;
	private boolean canDoBigCastling;
	private ArrayList<Piece> pieces;
	private int points;
	
	/**
	 * Constructor of the class
	 * @param name Name of the player
	 * @param color Color of the player
	 */
	public Player(final String name, final ColorG color, final Position kingPos)
	{
		this.name = name;
		this.color = color;
		this.kingPos = kingPos;
		this.canDoLittleCastling = true;
		this.canDoBigCastling = true;
		this.pieces = new ArrayList<Piece>();
		this.points = 0;
	}

	/**
	 * Get the name of the player
	 * @return The name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Get the name of the player
	 * @return The name
	 */
	public ColorG getColor() {
		return this.color;
	}

	/**
	 * Get the position of the player's king
	 * @return The position of the king
	 */
	public Position getKingPos() {
		return this.kingPos;
	}

	/**
	 * Set the position of the player's king
	 * @param kingPos The position
	 */
	public void setKingPos(final Position kingPos) {
		this.kingPos = kingPos;
	}

	/**
	 * Check if the player can do little castling
	 * @return true if it's possible
	 */
	public boolean isLittleCastlingPossible() {
		return this.canDoLittleCastling;
	}
	
	/**
	 * Set the possibility for the player to do little castling
	 * @param canDoCastling true or false
	 */
	public void setLittleCastling(final boolean canDoCastling) {
		this.canDoLittleCastling = canDoCastling;
	}
	
	/**
	 * Check if the player can do big castling
	 * @return true if it's possible
	 */
	public boolean isCastlingPossible() {
		return this.canDoBigCastling;
	}

	/**
	 * Set the possibility for the player to do big castling
	 * @param canDoCastling true or false
	 */
	public void setBigCastling(final boolean canDoCastling) {
		this.canDoBigCastling = canDoCastling;
	}

	/**
	 * A getter for the pieces array of the player
	 * @return All the remaining pieces of the player
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Piece> getPieces()
	{
		return (ArrayList<Piece>) this.pieces.clone();
	}

	/**
	 * Add a piece the user
	 * @param piece The piece to add
	 */
	public void addPiece(final Piece piece)
	{
		this.pieces.add(piece);
	}
	
	/**
	 * Remove a piece to the user
	 * @param piece the piece to remove
	 * @return true if the piece was removed, else false
	 */
	public boolean rmvPiece(final Piece piece)
	{
		return this.pieces.remove(piece);
	}
	
	/**
	 * Get the player's total points
	 * @return The points from eaten pieces
	 */
	public int getPoints()
	{
		return this.points;
	}
	
	/**
	 * Increase the number of points of the player
	 * @param nbPoints The number of points to add
	 */
	public void addPoints(final int nbPoints)
	{
		this.points += nbPoints;
	}
}
