package model;

import controller.Chessboard;

/**
 * Class to manage the Pawn piece
 * @author Hitsuji
 *
 */
public class Pawn extends Piece
{
	private boolean hasMoved;
	private boolean enPassant;
	private Piece piecePassant;
	
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
