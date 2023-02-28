package model;

import utils.ColorG;
import utils.Symbol;

/**
 * Class to manage the Pawn piece
 * @author Hitsuji
 *
 */
public class Pawn extends Piece
{
	/** Boolean to know if the pawn already moved */
	private boolean hasMoved;
	/** Boolean to know if the pawn did a 'enPassant' */
	private boolean enPassant;
	/** Enemy piece involved in the 'enPassant' */
	private Piece piecePassant;
	
	/**
	 * Constructor of the class
	 * @param board The chessboard that contains the piece
	 * @param pos The position of the piece
	 * @param color The color of the piece
	 * @param player The player who the piece belongs to
	 * @param value The value given if the piece is eaten
	 */
	public Pawn(Chessboard board, Position pos, final ColorG color, Player player, final int value) {
		super(board,
			  pos,
			  (color == ColorG.WHITE ? "White" : "Black") + " Pawn",
			  color,
			  (color == ColorG.WHITE ? Symbol.WHITE_PAWN : Symbol.BLACK_PAWN),
			  player,
			  value);
		this.hasMoved = false;
		this.enPassant = false;
	}
	
	@Override
	public boolean isValidMove(final Position pos)
	{
		Position piecePos = this.getPosition();
		int distance = piecePos.manhattanDistance(pos);
		
		if (distance <= 0 || 3 <= distance) {return false;}
		
		if (distance == 2 && piecePos.isOnSameDiagonal(pos)) {
			Piece target = board.getPiece(pos);
			
			if (target != null) {
				return this.getColor() != target.getColor();
				
			} else {
				Piece enPassant = this.board.getLastPawn();
				Position passantPos;
				Position oldPassantPos;
				
				if (enPassant == null || enPassant.getColor() == this.getColor()) {return false;}
				passantPos = enPassant.getPosition();
				oldPassantPos = new Position(passantPos.getX(),
											(passantPos.getY() + (enPassant.getColor() == ColorG.WHITE ? -1 : 1)));
				
				if (pos.equals(oldPassantPos)) {
					this.enPassant = true;
					this.piecePassant = enPassant;
					return true;
				}
			}
		}
		
		if ((this.hasMoved && distance == 2) || !piecePos.isOnSameColumn(pos)) {
			return false;
		}
		
		if (this.isWhite()) {
			return piecePos.getY() < pos.getY();
		} else {
			return piecePos.getY() > pos.getY();
		}
	}
	
	@Override
	public Piece moveTo(final Position pos)
	{
		Position piecePos = this.getPosition();
		Piece oldPiece;
		if (this.enPassant) {
			oldPiece = this.piecePassant;
			this.board.setPiece(null, oldPiece.getPosition());
		} else {
			oldPiece = this.board.getPiece(pos);
		}
		
		if (piecePos.manhattanDistance(pos) == 2 && piecePos.isOnSameColumn(pos)) {
			this.board.setLastPawn(this);
		} else {
			this.board.setLastPawn(null);
		}
		
		this.hasMoved = true;
		this.board.setPiece(null, piecePos);
		this.board.setPiece(this, pos);
		this.setPosition(pos);
		
		return oldPiece;
	}
}
