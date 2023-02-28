package model;

import utils.ColorG;
import utils.Symbol;

/**
 * Extended class to represents the Rook
 * @author erwann
 *
 */
public class Rook extends Piece
{
	/** Boolean to know if the rook already moved */
	private boolean hasMoved;
	
	/**
	 * Constructor of the class
	 * @param board The chessboard that contains the piece
	 * @param pos The position of the piece
	 * @param color The color of the piece
	 * @param player The player who the piece belongs to
	 * @param value The value given if the piece is eaten
	 */
	public Rook(Chessboard board, Position pos, final ColorG color, final Player player, final int value) {
		super(board,
			  pos,
			  (color == ColorG.WHITE ? "White" : "Black") + " Rook",
			  color,
			  (color == ColorG.WHITE ? Symbol.WHITE_ROOK : Symbol.BLACK_ROOK),
			  player,
			  value);
		
		this.hasMoved = false;
	}
	
	@Override
	public boolean isValidMove(final Position pos)
	{
		Position piecePos = this.getPosition();
		if (piecePos.manhattanDistance(pos) == 0) {
			return false;
		}
		
		return piecePos.isOnSameColumn(pos) ||
			   piecePos.isOnSameLine(pos);
	}
	
	@Override
	public Piece moveTo(final Position pos)
	{
		Piece oldPiece = super.moveTo(pos);
		King king = (King) this.board.getPiece(this.getPlayer().getKingPos());
		Position piecePos = this.getPosition();
		
		if (!this.hasMoved) {
			if (piecePos.getX() == 0) {
				king.disableBigCastling();
			}
			else if (piecePos.getX() == 7) {
				king.disableLittleCastling();
			}
			this.hasMoved = true;
		}
		return oldPiece;
	}
}