package model;

import utils.ColorG;
import utils.Symbol;

/**
 * Class to manage the King piece
 * @author Hitsuji
 */
public class King extends Piece
{
	/** Boolean to look if the king already moved */
	private boolean hasMoved;
	/** Boolean to look if the king did a castle */
	private boolean hasDoneCastle;
	/** Boolean to look if the king did little/big castle */
	private boolean doLittleCastle, doBigCastle;
	/** Boolean to look if the little/big castle are disabled */
	private boolean disableLittleC, disableBigC;
	/** The piece involved in the castle*/
	private Piece castlePiece;
	
	/**
	 * Constructor of the class
	 * @param board The chessboard that contains the piece
	 * @param pos The position of the piece
	 * @param color The color of the piece
	 * @param player The player who the piece belongs to
	 * @param value The value given if the piece is eaten
	 */
	public King(Chessboard board, Position pos, final ColorG color, final Player player, final int value) {
		super(board,
			  pos,
			  (color == ColorG.WHITE ? "White" : "Black") + " King",
			  color,
			  (color == ColorG.WHITE ? Symbol.WHITE_KING : Symbol.BLACK_KING),
			  player,
			  value);
		
		this.hasDoneCastle = false;
		this.doLittleCastle = false;
		this.doBigCastle = false;
		this.disableLittleC = false;
		this.disableBigC = false;
		this.castlePiece = null;
	}
	
	/**
	 * See if the king did a little castle movement
	 * @return true or false
	 */
	public boolean hasDoneLittleCastling()
	{
		return this.doLittleCastle;
	}
	
	/**
	 * See if the king did a big castle movement
	 * @return true or false
	 */
	public boolean hasDoneBigCastling()
	{
		return this.doBigCastle;
	}
	
	/**
	 * Reset the castle movement attributes of the king
	 */
	public void resetCastle()
	{
		this.doLittleCastle = false;
		this.doBigCastle = false;
		this.castlePiece = null;
	}
	
	/**
	 * Disable the possibility to realize a little castling
	 */
	public void disableLittleCastling()
	{
		this.disableLittleC = true;
	}
	
	/**
	 * Disable the possibility to realize a big castling
	 */
	public void disableBigCastling()
	{
		this.disableBigC = true;
	}
	
	@Override
	public boolean isValidMove(final Position pos)
	{
		Position piecePos = this.getPosition();
		int yPos = (this.getSymbol() == Symbol.WHITE_KING ? 0 : 7);
		int distance = piecePos.manhattanDistance(pos);
		
		if (piecePos.equals(new Position(4, yPos)) && piecePos.isOnSameLine(pos) && distance == 2) { // Check if the player try to do a castle
			if (this.hasDoneCastle) {return false;}
			
			Piece pieceC; // Supposed to contain the rook for the castling
			if (piecePos.getX() > pos.getX()) { // The player want to do a big castling
				if (this.disableBigC) {return false;}
				
				pieceC = this.board.getPiece(0, yPos);// first rook
				if (pieceC == null ||
					this.getColor() != pieceC.getColor() ||
					(pieceC.getSymbol() != Symbol.WHITE_ROOK && pieceC.getSymbol() != Symbol.BLACK_ROOK)) {return false;}
				
				for (int x = 3; x >= pieceC.getPosition().getX(); x--) {
					if (0 < x && x < 4) {
						if (this.board.getPiece(x, yPos) != null) {return false;}
					}
					if (!this.board.safeSquare(new Position(x, yPos),
						(this.getPlayer() == this.board.getWhitePlayer() ? this.board.getBlackPlayer() : this.board.getWhitePlayer()))) 
						{return false;}
				}
				
				this.doBigCastle = true;
				
			} 
			else if (piecePos.getX() < pos.getX()) {
				if (this.disableLittleC) {return false;}
				
				pieceC = this.board.getPiece(7, yPos); // 2nd rook
				if (pieceC == null ||
					this.getColor() != pieceC.getColor() ||
					(pieceC.getSymbol() != Symbol.WHITE_ROOK && pieceC.getSymbol() != Symbol.BLACK_ROOK)) {return false;}
				
				for (int x = 4; x <= pieceC.getPosition().getX(); x++) {
					if (4 < x && x < 7) {
						if (this.board.getPiece(x, yPos) != null) {return false;}
					}
					if (!this.board.safeSquare(new Position(x, yPos),
						(this.getPlayer() == this.board.getWhitePlayer() ? this.board.getBlackPlayer() : this.board.getWhitePlayer()))) 
						{return false;}
				}
				
				this.doLittleCastle = true;
			} 
			else {
				return false;
			}
			this.castlePiece = pieceC;
			return true;
		}
		
		return (piecePos.isOnSameDiagonal(pos) && piecePos.manhattanDistance(pos) == 2) ||
			   ((piecePos.isOnSameColumn(pos) || piecePos.isOnSameLine(pos)) && piecePos.manhattanDistance(pos) == 1);
	}
	
	@Override
	public boolean canMoveTo(final Position pos)
	{
		Piece piece = board.getPiece(pos);
		Position piecePos = this.getPosition();
		
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
		
		return this.board.checkSquare(pos, this,
									  this.isWhite() ? this.board.getBlackPlayer() : this.board.getWhitePlayer());
	}
	
	@Override 
	public Piece moveTo(final Position pos)
	{
		int yPos = (this.isWhite() ? 0 : 7);
		
		if (this.doLittleCastle) {
			this.castlePiece.moveTo(new Position(5, yPos));
			this.hasDoneCastle = true;
		}
		else if (this.doBigCastle) {
			this.castlePiece.moveTo(new Position(3, yPos));
			this.hasDoneCastle = true;
		}
		
		if (!this.hasMoved) {
			this.disableLittleCastling();
			this.disableBigCastling();
			this.hasMoved = true;
		}

		Piece oldPiece = super.moveTo(pos);
		this.getPlayer().setKingPos(this.getPosition());
		return oldPiece;
	}
}
