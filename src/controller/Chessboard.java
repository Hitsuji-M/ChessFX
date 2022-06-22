package controller;

import model.*;

/**
 * Class to manage the board of the game
 * @author Hitsuji
 */
public class Chessboard
{
	private Piece[][] pieces;
	private Piece lastPawnHop; 
	private Player whitePlayer, blackPlayer;
	
	/**
	 * Constructor of the class
	 */
	public Chessboard(final Player white, final Player black)
	{
		this.pieces = new Piece[8][8];
		this.whitePlayer = white;
		this.blackPlayer = black;
		
		this.setPieces(white, black);
	}
	
	/**
	 * Set all the pieces on the board
	 */
	private void setPieces(final Player white, final Player black)
	{
		for (int i = 0; i < 8; i++) {
			this.pieces[i][1] = new Pawn(this, new Position(i, 1), ColorG.WHITE, white, 1);
			this.pieces[i][6] = new Pawn(this, new Position(i, 6), ColorG.BLACK, black, 1);
		}
		
		this.pieces[0][0] = new Rook(this, new Position(0, 0), ColorG.WHITE, white, 5);
		this.pieces[1][0] = new Knight(this, new Position(1, 0), ColorG.WHITE, white, 3);
		this.pieces[2][0] = new Bishop(this, new Position(2, 0), ColorG.WHITE, white, 3);
		this.pieces[3][0] = new Queen(this, new Position(3, 0), ColorG.WHITE, white, 9);
		this.pieces[4][0] = new King(this, new Position(4, 0), ColorG.WHITE, white, 0);
		this.pieces[5][0] = new Bishop(this, new Position(5, 0), ColorG.WHITE, white, 3);
		this.pieces[6][0] = new Knight(this, new Position(6, 0), ColorG.WHITE, white, 3);
		this.pieces[7][0] = new Rook(this, new Position(7, 0), ColorG.WHITE, white, 5);
		
		this.pieces[0][7] = new Rook(this, new Position(0, 7), ColorG.BLACK, black, 5);
		this.pieces[1][7] = new Knight(this, new Position(1, 7), ColorG.BLACK, black, 3);
		this.pieces[2][7] = new Bishop(this, new Position(2, 7), ColorG.BLACK, black, 3);
		this.pieces[3][7] = new Queen(this, new Position(3, 7), ColorG.BLACK, black, 9);
		this.pieces[4][7] = new King(this, new Position(4, 7), ColorG.BLACK, black, 0);
		this.pieces[5][7] = new Bishop(this, new Position(5, 7), ColorG.BLACK, black, 3);
		this.pieces[6][7] = new Knight(this, new Position(6, 7), ColorG.BLACK, black, 3);
		this.pieces[7][7] = new Rook(this, new Position(7, 7), ColorG.BLACK, black, 5);
	}
	
	/**
	 * Get a piece from the board
	 * @param x The x position of the piece
	 * @param y The y position of the piece
	 * @return The piece at the XY position
	 */
	public Piece getPiece(final int x, final int y)
	{
		return this.pieces[x][y];
	}
	
	/**
	 * Get a piece from the board
	 * @param pos the position searched
	 * @return The piece at the given position
	 */
	public Piece getPiece(final Position pos)
	{
		return this.pieces[pos.getX()][pos.getY()];
	}
	
	/**
	 * Set the piece on the board
	 * @param piece The piece to set
	 * @param x The new x position of the piece
	 * @param y The new y position of the piece
	 */
	public void setPiece(final Piece piece, final int x, final int y)
	{
		this.pieces[x][y] = piece;
	}
	
	/**
	 * Set the piece on the board
	 * @param piece The piece to set
	 * @param pos The new position of the piece
	 */
	public void setPiece(final Piece piece, final Position pos)
	{
		this.setPiece(piece, pos.getX(), pos.getY());
	}
	
	/**
	 * Get the last pawn that done a big jump (2 squares)
	 * @return A piece from the board
	 */
	public Piece getLastPawn()
	{
		return this.lastPawnHop;
	}
	
	/**
	 * Change the last pawn that done a big jump
	 * @param piece The new piece
	 */
	public void setLastPawn(final Piece piece)
	{
		this.lastPawnHop = piece;
	}
	
	/**
	 * Get the white player of the game
	 * @return A player
	 */
	public Player getWhitePlayer()
	{
		return this.whitePlayer;
	}
	
	/**
	 * Get the black player of the game
	 * @return A player
	 */
	public Player getBlackPlayer()
	{
		return this.blackPlayer;
	}
	
	/**
	 * Check if there's a piece between the start and the end 
	 * @param start The starting position
	 * @param end The ending position
	 * @return Returns true if there's a position, else false
	 */
	public boolean isPieceBetweenInLine(final Position start, final Position end)
	{
		if (!start.isOnSameLine(end)) {
			throw new IllegalArgumentException("Not on the same line");
		}
		
		int min = Math.min(start.getX(), end.getX());
		int max = Math.max(start.getX(), end.getX());
		int y = start.getY();
		
		for (int i = min+1; i < max; i++) {
			if (this.getPiece(i, y) != null) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check if there's a piece between the start and the end 
	 * @param start The starting position
	 * @param end The ending position
	 * @return Returns true if there's a position, else false
	 */
	public boolean isPieceBetweenInColumn(final Position start, final Position end)
	{
		if (!start.isOnSameColumn(end)) {
			throw new IllegalArgumentException("Not on the same column");
		}
		
		int min = Math.min(start.getY(), end.getY());
		int max = Math.max(start.getY(), end.getY());
		int x = start.getX();
		
		for (int i = min+1; i < max; i++) {
			if (this.getPiece(x, i) != null) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check if there's a piece between the start and the end 
	 * @param start The starting position
	 * @param end The ending position
	 * @return Returns true if there's a position, else false
	 */
	public boolean isPieceBetweenInDiagonal(final Position start, final Position end)
	{
		if (!start.isOnSameDiagonal(end) || start.equals(end)) {
			throw new IllegalArgumentException("Not on the same diagonal or it's the same position");
		}
		
		int addX = (start.getX() < end.getX() ? 1 : -1);
		int addY = (start.getY() < end.getY() ? 1 : -1);
		
		int x, y;
		x = start.getX() + addX;
		y = start.getY() + addY;;
		
		while (x != end.getX() && y != end.getY()) {
			if (this.getPiece(x, y) != null) {
				return true;
			}
			x += addX;
			y += addY;
		}
		return false;
	}
	
	/**
	 * Simulate the movement of the piece before checking the square
	 * @param pos The ending position of the piece
	 * @param piece The moving piece
	 * @param player The opponent
	 * @return true if the square is safe, else false
	 */
	public boolean checkSquare(final Position pos, final Piece piece, final Player player)
	{
		Piece opponentPiece = this.getPiece(pos);
		this.setPiece(null, piece.getPosition());
		this.setPiece(piece, pos);
		
		if (opponentPiece != null) {
			player.rmvPiece(opponentPiece);
		}
		
		boolean res = this.safeSquare(pos, player);
		if (opponentPiece != null) {
			player.addPiece(opponentPiece);
		}
		
		this.setPiece(opponentPiece, pos);
		this.setPiece(piece, piece.getPosition());
		return res;
	}
	
	/**
	 * Simulate the movement of the piece before checking the square
	 * @param kingPos The position of the king
	 * @param piece The moving piece
	 * @param end The ending position of the piece
	 * @param player The opponent
	 * @return true if the square is safe, else false
	 */
	public boolean checkSquare(final Position kingPos, final Piece piece, final Position end, final Player player)
	{
		Piece opponentPiece = this.getPiece(end);
		this.setPiece(null, piece.getPosition());
		this.setPiece(piece, end);
		
		if (opponentPiece != null) {
			player.rmvPiece(opponentPiece);
		}
		
		boolean res = this.safeSquare(kingPos, player);
		if (opponentPiece != null) {
			player.addPiece(opponentPiece);
		}
		
		this.setPiece(opponentPiece, end);
		this.setPiece(piece, piece.getPosition());
		return res;
	}
	
	/**
	 * Check if the square is safe
	 * @param pos The position of the square to check
	 * @param player The opponent
	 * @return true if none of the pieces can reach this square
	 */
	public boolean safeSquare(final Position pos, final Player player)
	{
		for (Piece piece : player.getPieces()) {
			if (piece.canMoveTo(pos)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public String toString()
	{
		StringBuilder res = new StringBuilder("");
		
		// Loop creation for the 8 lines
		for (int i = 0; i < 8; i++) {
			
			// First line creation (edge of the board)
			for (int j1 = 0; j1 < 8; j1++) {
				res.append( "-------");
			}
			res.append( "\n");
			
			// One square has a dimension of 5x3 (not counting the edge
			// Drawing the top of the square
			for (int j2 = 0; j2 < 8; j2++) {
				res.append( "|     |");
			}
			res.append("\n");
			
			// If it contains a piece, writing 1
			for (int j3 = 0; j3 < 8; j3++) {
				res.append("|  " + (this.getPiece(i, j3) == null ? "   |" : (this.getPiece(i, j3).getSymbol() + "  |")));
			}
			//  At the end of the line we add the corresponding letter
			res.append(" " + ((char) (i+65)) + "\n");
			
			// Adding the bottom
			for (int j4 = 0; j4 < 8; j4++) {
				res.append("|     |");
			}
			res.append("\n");
		}
		
		// Adding the bottom edge of the last line
		for (int end = 0; end < 8; end++) {
			res.append("-------");
		}
		res.append("\n");
		
		// Adding the number of the column
		for (int num = 0; num < 8; num++) {
			res.append("   " + (num+1) + "   ");
		}
		
		return res.toString();
	}
}
