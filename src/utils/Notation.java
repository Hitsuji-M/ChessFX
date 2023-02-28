package utils;

import model.Piece;
import model.Position;

/**
 * A class to create algebraic notation of the moves in chess
 * @author Hitsuji
 */
public class Notation
{
	/**
	 * Disabled default constructor
	 */
	private Notation() {}
	
	/**
	 * The base of classic and promotion notations
	 * @param piece The piece that moved
	 * @param capture If the piece did a capture or not
	 * @return Base of a notatio,
	 */
	private static String base(final Piece piece, final boolean capture)
	{
		Position pos = piece.getPosition();
		char xChar = (char) (pos.getX()+97);
		return (capture ? "x" : "") + xChar + (pos.getY() + 1);
	}
	
	/**
	 * Format for the en passant
	 * @param enPassant if the piece did en passant or not
	 * @return The string format of the parameter
	 */
	private static String enPassantStr(final boolean enPassant)
	{
		return (enPassant ? "e.p." : " ");
	}
	
	/**
	 * Format for a check
	 * @param enPassant if the piece checks the king
	 * @return The string format of the check
	 */
	private static String checkStr(final boolean check)
	{
		return (check ? "+" : "");
	}
	
	/**
	 * The default notation of a move
	 * @param piece The piece moved
	 * @param capture If the piece captured another
	 * @param check If the piece put the enemy king in check
	 * @param enP If the it was a enPassant or not
	 * @param oldX The ancient position in x of the piece
	 * @return The notation of the move
	 */
	public static String classicNotation(final Piece piece, final boolean capture, final boolean check, final boolean enP, final int oldX)
	{
		String start = "";
		if (piece.getSymbol() != Symbol.WHITE_PAWN && piece.getSymbol() != Symbol.BLACK_PAWN) {
			start += piece.initialOfPiece(oldX);
		} 

		return start + base(piece, capture) + checkStr(check) + enPassantStr(enP);
	}
	
	/**
	 * The notation of a promotion
	 * @param piece The piece moved
	 * @param capture If the piece captured another
	 * @param check If the piece put the enemy king in check
	 * @param enP If the it was a enPassant or not
	 * @param oldX The ancient position in x of the piece
	 * @return The notation of the move
	 */
	public static String promotionNotation(final Piece piece, final boolean capture, final boolean check, final boolean enP, final int oldX)
	{
		char symbol = piece.initialOfPiece(oldX);
		return "" + base(piece, capture) + "=" + symbol + checkStr(check) + enPassantStr(enP);
	}
	
	/**
	 * Give the algebraic notation of a castle
	 * @param little If it's a little castling or a big
	 * @param check If the piece put the enemy king in check
	 * @return The notation
	 */
	public static String castleNotation(final boolean little, final boolean check)
	{
		return (little ? "0-0" : "0-0-0") + (check ? "+" : "");
	}
}
