package model;

import controller.Chessboard;

public class Knight extends Piece
{
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
