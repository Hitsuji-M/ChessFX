package model;

import controller.Chessboard;

/**
 * Class to manage all the piece of the game
 * @author Hitsuji
 */
public abstract class Piece
{
	private Position position;
	private ColorG color;
	private String name;
	private char symbol;
	private Player player;
	protected Chessboard board;
	private int value;
	
	/**
	 * Constructor for the Class
	 * @param board The board of the game
	 * @param pos The position of the piece on the board
	 * @param color The color of the piece (BLACK or WHITE)
	 * @param name The name of the piece
	 */
	public Piece(final Chessboard board, final Position pos, final String name, final ColorG color, final char symbol, final Player player, final int value)
	{
		this.position = pos;
		this.color = color;
		this.name = name;
		this.board = board;
		this.symbol = symbol;
		this.player = player;
		this.value = value;
	}

	/**
	 * Getter of the position 
	 * @return The position of the piece
	 */
	public Position getPosition()
	{
		return this.position;
	}
	
	/**
	 * Get the color of the piece
	 * @return The color
	 */
	public ColorG getColor() 
	{
		return this.color;
	}
	
	/**
	 * Get the name of the piece
	 * @return The name of the piece
	 */
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * Get the symbol of the piece
	 * @return The UTF-16 character
	 */
	public char getSymbol()
	{
		return this.symbol;
	}
	
	/**
	 * Get the player who owns the piece
	 * @return A player of the game
	 */
	public Player getPlayer()
	{
		return this.player;
	}
	
	/**
	 * Check if the piece is black
	 * @return true if the piece is black, if white returns false
	 */
	public boolean isBlack()
	{
		return this.color == ColorG.BLACK;
	}
	
	/**
	 * Check if the piece is white
	 * @return true if the piece is white, if black returns false
	 */
	public boolean isWhite()
	{
		return this.color == ColorG.WHITE;
	}
	
	/**
	 * Get the number of points that values the piece
	 * @return Value of the piece
	 */
	public int getValue()
	{
		return this.value;
	}
	
	/**
	 * Get the initial corresponding for the piece
	 * @param xPos the ancient position in x of the piece
	 * @return 'K' for King / 'Q' for Queen / 'N' for Knight / 'B' for Bishop / 'R' for Rook
	 */
	public char initialOfPiece(final int xPos)
	{
		char symbol = this.getSymbol();
		
		if (symbol == Symbol.BLACK_BISHOP || symbol == Symbol.WHITE_BISHOP) {
			return 'B';
		}
		else if (symbol == Symbol.BLACK_KING || symbol == Symbol.WHITE_KING) {
			return 'K';
		}
		else if (symbol == Symbol.BLACK_QUEEN || symbol == Symbol.WHITE_QUEEN) {
			return 'Q';
		}
		else if (symbol == Symbol.BLACK_ROOK || symbol == Symbol.WHITE_ROOK) {
			return 'R';
		}
		else if (symbol == Symbol.BLACK_KNIGHT || symbol == Symbol.WHITE_KNIGHT) {
			return 'N';
		}
		else {
			return (char) (xPos+97);
		}
	}
	
	/**
	 * Check if the piece can move to the position on the board
	 * @param pos The position to reach
	 * @return True if it can reach, else false
	 */
	public boolean canMoveTo(final Position pos)
	{
		Piece piece = board.getPiece(pos);
		Position piecePos = this.getPosition();
		ColorG colorP = this.getPlayer().getColor();
		
		if (pos == null) {
			return false;
		}
		
		if (!this.isValidMove(pos)) {
			return false;
		}
		
		if (piece != null && piece.getColor() == this.getColor()) {
			return false;
		}
		
		if (piecePos.isOnSameColumn(pos) && board.isPieceBetweenInColumn(piecePos, pos)) {
			return false;
		}
		if (piecePos.isOnSameLine(pos) && board.isPieceBetweenInLine(piecePos, pos)) {
			return false;
		}
		if (piecePos.isOnSameDiagonal(pos) && board.isPieceBetweenInDiagonal(piecePos, pos)) {
			return false;
		}
		
		return this.board.checkSquare(this.getPlayer().getKingPos(), this, pos,
								   	  colorP == ColorG.WHITE ? this.board.getBlackPlayer(): this.board.getWhitePlayer());
	}
	
	/**
	 * Move the piece
	 */
	public Piece moveTo(final Position pos)
	{
		Piece oldPiece = this.board.getPiece(pos);
		
		this.board.setLastPawn(null);
		this.board.setPiece(null, this.getPosition());
		this.board.setPiece(this, pos);
		this.setPosition(pos);
		
		return oldPiece;
	}
	
	/**
	 * Set the new position
	 * @param pos The new position
	 */
	public void setPosition(final Position pos)
	{	
		Position posPiece = this.getPosition();
		posPiece.setX(pos.getX());
		posPiece.setY(pos.getY());
	}
	
	@Override
	public String toString()
	{
		return this.getName() + " : " + this.getPosition();
	}
	
	/**
	 * Check if the move is valid for the piece
	 * @param pos The position to reach
	 * @return true if it's valid, else false
	 */
	public abstract boolean isValidMove(Position pos);

}
