package main;

import model.*;
import utils.*;

import java.util.ArrayList;

/**
 * The class to manage the game and how it's working
 * 
 * @author Hitsuji
 */
public class GameEngine {
	/** UI of the game */
	private UserInterface ui;
	/** The board of the game */
	private Chessboard board;
	/** White and Black player of the current game */
	private Player whitePlayer, blackPlayer;
	/** The player currently playing */
	private Player currentPlayer;
	/** An Array of the moves done during the game */
	private ArrayList<String[]> moves;
	/** Current state of the game 0 for playing, 1 for a win */
	private int state;
	/** Number of turns where nothing happened */
	private int countNullTurn;

	/**
	 * Constructor of the class
	 */
	public GameEngine() {
		this.board = new Chessboard(this.whitePlayer, this.blackPlayer);
		this.moves = new ArrayList<String[]>();
		this.state = 0;
		this.countNullTurn = 0;
	}
	
	/**
	 * Get the current state of the game
	 * @return The state of the game
	 */
	public int getState()
	{
		return this.state;
	}
	
	/**
	 * Gives the current player of the turn
	 * @return Player
	 */
	public Player getCurrentPlayer()
	{
		return this.currentPlayer;
	}
	
	/**
	 * Set the UserInterface of the game
	 * @param ui The UserInterface object
	 */
	public void setUI(final UserInterface ui)
	{
		this.ui = ui;
	}
	
	public String turnInfo()
	{
		return this.board + "\n\n"
				+ this.currentPlayer.getName() + "'s turn\n"
				+ "- Color ==> " + this.currentPlayer.getColor() + "\n"
				+ "- Total points ==> " + this.currentPlayer.getPoints() + "\n";
	}
	
	public String endInfo()
	{
		String res = this.board + "\n";
		
		if (this.state == 1) {
			res += this.currentPlayer + " has won\n";
		} else {
			res += "The game ended null, no one won\n";
		}
		return res;
	}
	
	/**
	 * Add players to the game
	 * @param whiteP Name of the white player
	 * @param blackP Name of the black player
	 */
	public void addPlayers(final String whiteP, final String blackP)
	{
		this.whitePlayer = new Player(whiteP, ColorG.WHITE, new Position(4, 0));
		this.blackPlayer = new Player(blackP, ColorG.BLACK, new Position(4, 7));
		this.currentPlayer = this.whitePlayer;
		this.board.updatePlayers(this.whitePlayer, this.blackPlayer);
	}
	
	/**
	 * Change the state of the game and prepare for a forfeit
	 * @return The end message
	 */
	public String forfeit()
	{
		String res = this.currentPlayer.getName() + " forfeited";
		this.switchPlayer();
		this.state = 1;
		return res;
	}


	/**
	 * Add a move to the list of game's moves
	 */
	private void addMove(final Piece piece, final boolean capture,
					     final boolean check, final boolean promotion,
						 final int castleType, final boolean enPassant, final int oldX) {
		final String notation;
		if (promotion) {
			notation = Notation.promotionNotation(piece, capture, check, enPassant, oldX);
		} else if (castleType != 0) {
			notation = Notation.castleNotation((castleType == 1 ? true : false), check);
		} else {
			notation = Notation.classicNotation(piece, capture, check, enPassant, oldX);
		}

		if (this.currentPlayer.getColor() == ColorG.WHITE) {
			String[] couple = new String[2];
			couple[0] = notation;
			couple[1] = "";
			this.moves.add(couple);
		} else {
			String[] lastCouple = this.moves.get(this.moves.size() - 1);
			lastCouple[1] = notation;
		}
	}

	/**
	 * Display all the moves used in the game
	 */
	public String displayMoves() {
		StringBuilder res = new StringBuilder("");
		for (int i = 0; i < this.moves.size(); i++) {
			String[] couple = this.moves.get(i);
			res.append("" + (i + 1) + " : " + couple[0] + "\t" + couple[1] + "\n");
		}
		return res + "\n";
	}

	/**
	 * Switch current player
	 */
	private void switchPlayer() {
		if (this.currentPlayer == this.whitePlayer) {
			this.currentPlayer = this.blackPlayer;
		} else {
			this.currentPlayer = this.whitePlayer;
		}
	}

	/**
	 * Ask for a draw to stop the game
	 * 
	 * @return true if the player agreed, else false
	 */
	public boolean askForDraw() {
		String answer = this.ui.askDraw().toLowerCase();
		if (answer.equals("y") || answer.equals("yes")) {
			this.state = 2;
			return true;
		}
		return false;
	}

	/**
	 * Check if a player has lost due to checkmate
	 * 
	 * @param playerInDanger The player to check
	 * @param attacker       The opponent
	 * @return true if the player has lost, else false
	 */
	private boolean isCheckmate(final Player playerInDanger, final Player attacker) {
		Position targetPos;
		boolean res;

		for (Piece currentPiece : playerInDanger.getPieces()) {
			
			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					targetPos = new Position(x, y);

					if (currentPiece.canMoveTo(targetPos)) {
						
						if (currentPiece.getSymbol() == Symbol.WHITE_KING ||
							currentPiece.getSymbol() == Symbol.BLACK_KING) {
							
							res = this.board.checkSquare(targetPos, currentPiece, attacker);
						} else {
							res = this.board.checkSquare(playerInDanger.getKingPos(), currentPiece, targetPos,
									attacker);
						}

						if (res) {
							
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * Promote a pawn
	 * 
	 * @param oldPawn The pawn to promote
	 * @return Return the piece that will replace the pawn
	 */
	private Piece doPromotion(final Piece oldPawn) {
		char choice;
		String inputUser;
		Player player = oldPawn.getPlayer();
		Position pos = oldPawn.getPosition();
		ColorG color = oldPawn.getColor();

		while (true) {
			inputUser = this.ui.askPromotion(pos).toUpperCase();
			if (inputUser.length() > 1) {
				continue;
			}
			choice = inputUser.charAt(0);

			switch (choice) {
			case 'N':
				return new Knight(this.board, pos, color, player, 1);
			case 'B':
				return new Bishop(this.board, pos, color, player, 1);
			case 'R':
				return new Rook(this.board, pos, color, player, 1);
			case 'Q':
				return new Queen(this.board, pos, color, player, 1);
			}
		}
	}

	/**
	 * Manage all the actions of the turn
	 * 
	 * @param start The starting position of the player
	 * @param end   The ending position of the player
	 * @throws ChessMoveException Error launched if the player asked for an
	 *                            impossible move
	 */
	public void turn(final Position start, final Position end) throws ChessMoveException {
		// All the useful data
		Piece pieceStart = this.board.getPiece(start);
		Piece oldPiece;
		Player currentP = this.currentPlayer;
		Player enemy = (currentP == this.whitePlayer ? this.blackPlayer : this.whitePlayer);
		boolean check = false;
		boolean promotion = false;
		boolean enPassant = false;
		int castle = 0;
		int oldX = -1;
		char symbolP;

		// Stop the function in case of a wrong move
		if (pieceStart == null || pieceStart.getColor() != currentP.getColor()) {
			throw new ChessMoveException("This move is impossible, choose one of your piece", start.toString(),
					end.toString());
		}
		
		// Move the piece if it's possible
		if (pieceStart.canMoveTo(end)) {
			symbolP = pieceStart.getSymbol();
			oldPiece = pieceStart.moveTo(end);
		} else {
			throw new ChessMoveException("This move is impossible ", start.toString(), end.toString());
		}

		// If the piece is a pawn, promote it if needed
		if ((symbolP == Symbol.WHITE_PAWN && end.getY() == 7) || (symbolP == Symbol.BLACK_PAWN && end.getY() == 0)) {
			oldX = start.getX();			
			Piece piecePromotion = this.doPromotion(pieceStart);
			this.board.setPiece(piecePromotion, end);
			pieceStart = piecePromotion;
			promotion = true;
			
			enPassant = start.getX() != end.getX() && !pieceStart.getPosition().equals(oldPiece.getPosition());
		}

		// If the moving piece is a king check if it was a castle
		if (symbolP == Symbol.WHITE_KING || symbolP == Symbol.BLACK_KING) {
			King kingPiece = (King) pieceStart;
			if (kingPiece.hasDoneLittleCastling()) {
				castle = 1;
				kingPiece.resetCastle();
			} else if (kingPiece.hasDoneBigCastling()) {
				castle = 2;
				kingPiece.resetCastle();
			}
		}
		
		// Delete the eaten piece from the enemy list and increase player points
		if (oldPiece != null) {
			enemy.rmvPiece(oldPiece);
			currentP.addPoints(oldPiece.getValue());
		}

		// Put the player in check if his king is not safe
		if (!this.board.safeSquare(enemy.getKingPos(), currentP)) {
			check = true;
			this.addMove(pieceStart, (oldPiece == null ? false : true), check, promotion, castle, enPassant, oldX);

			if (this.isCheckmate(enemy, currentP)) {
				this.state = 1;
				this.ui.setEndMsg(this.currentPlayer + " put the opposing king in checkmate");
				return;
			} else {
				this.switchPlayer();
			}
		} else {
			this.addMove(pieceStart, (oldPiece == null ? false : true), check, promotion, castle, enPassant, oldX);
			this.switchPlayer();
		}

		// If there's no check, we look for a stalemate (player can't play)
		if (!check) {
			if (this.isCheckmate(enemy, currentP)) { // The stalemate works like a checkmate but the king is not in check
				this.state = 2;
				this.ui.setEndMsg("The game is in a situation of stalemate, it's considered as null");
				return;
			}
		}

		// In case of no piece eaten and the moving piece is a pawn, increase the
		// counter.
		// If the counter is at 50 or more the game is null
		if (oldPiece == null && (symbolP == Symbol.WHITE_PAWN || symbolP == Symbol.BLACK_PAWN)) {
			this.countNullTurn++;
			if (this.countNullTurn >= 50) {
				this.state = 2;
				this.ui.setEndMsg("No pieces have been captured and no pawns have been moved during the last 50 turns,"
							+ "this game is declared as null !");
			}
		} else {
			this.countNullTurn = 0;
		}
	}
}
