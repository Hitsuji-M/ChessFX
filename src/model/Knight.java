package model;

import utils.ColorG;
import utils.Symbol;

/**
 * Extended class to represents the Knight
 * @author erwann
 *
 */
public class Knight extends Piece
{
	/**
	 * Constructor of the class
	 * @param board The chessboard that contains the piece
	 * @param pos The position of the piece
	 * @param color The color of the piece
	 * @param player The player who the piece belongs to
	 * @param value The value given if the piece is eaten
	 */
	public Knight(Chessboard board, Position pos, final ColorG color, final Player player, final int value) {
		super(board,
			  pos,
			  (color == ColorG.WHITE ? "White" : "Black") + " Knight",
			  color,
			  (color == ColorG.WHITE ? Symbol.WHITE_KNIGHT : Symbol.BLACK_KNIGHT),
			  player,
			  value);
	}
	
	@Override
	public boolean isValidMove(final Position pos)
	{
		Position piecePos = this.getPosition();
		return piecePos.manhattanDistance(pos) == 3 &&
			   !piecePos.isOnSameColumn(pos) &&
			   !piecePos.isOnSameLine(pos);
	}
	
	@Override
	public boolean canMoveTo(final Position pos)
	{	
		Piece piece = board.getPiece(pos);
		ColorG colorP = this.getColor();
		
		if (pos == null) {
			return false;
		}
		
		if (!this.isValidMove(pos)) {
			return false;
		}
		
		if (piece != null && piece.getColor() == this.getColor()) {
			return false;
		}
		
		return this.board.checkSquare(this.getPlayer().getKingPos(), this, pos,
			   	  					  colorP == ColorG.WHITE ? this.board.getBlackPlayer(): this.board.getWhitePlayer());
	}
}
