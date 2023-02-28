package model;

import utils.ColorG;
import utils.Symbol;

public class Bishop extends Piece
{

	public Bishop(Chessboard board, Position pos, final ColorG color, final Player player, final int value) {
		super(board,
			  pos,
			  (color == ColorG.WHITE ? "White" : "Black") + " Bishop",
			  color,
			  (color == ColorG.WHITE ? Symbol.WHITE_BISHOP : Symbol.BLACK_BISHOP),
			  player,
			  value);
	}
	
	@Override
	public boolean isValidMove(final Position pos)
	{
		Position piecePos = this.getPosition();
		return piecePos.manhattanDistance(pos) > 0 && piecePos.isOnSameDiagonal(pos);
	}

}
