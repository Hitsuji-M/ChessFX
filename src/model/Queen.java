package model;

import controller.Chessboard;

public class Queen extends Piece
{
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
