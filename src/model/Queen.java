package model;

import utils.ColorG;
import utils.Symbol;

/**
 * Extended class to represents the Queen
 * @author erwann
 *
 */
public class Queen extends Piece
{
	/**
	 * Constructor of the class
	 * @param board The chessboard that contains the piece
	 * @param pos The position of the piece
	 * @param color The color of the piece
	 * @param player The player who the piece belongs to
	 * @param value The value given if the piece is eaten
	 */
	public Queen(Chessboard board, Position pos, final ColorG color, final Player player, final int value) {
		super(board,
			  pos,
			  (color == ColorG.WHITE ? "White" : "Black") + " Queen",
			  color,
			  (color == ColorG.WHITE ? Symbol.WHITE_QUEEN : Symbol.BLACK_QUEEN),
			  player,
			  value);
	}
	
	@Override
	public boolean isValidMove(final Position pos)
	{
		Position piecePos = this.getPosition();
		if (piecePos.manhattanDistance(pos) == 0) {
			return false;
		}
		
		return piecePos.isOnSameColumn(pos) ||
			   piecePos.isOnSameLine(pos) ||
			   piecePos.isOnSameDiagonal(pos);
	}
}
