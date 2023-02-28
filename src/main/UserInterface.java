package main;

import model.Position;
import utils.ChessMoveException;

import java.util.Scanner;

/**
 * Class to manage the UserInterface such as user inputs, outputs, ...
 * @author erwann
 *
 */
public class UserInterface
{
	/** The GameEngine to run the project*/
	private GameEngine ge;
	/** The scanner for all user inputs */
	private Scanner scanner;
	/** Final message to show at the end of the game */
	private String endMsg;
	
	/**
	 * Constructor of the class
	 * @param ge The GameEngine of the project that will be linked to the UI
	 */
	public UserInterface(final GameEngine ge)
	{
		this.ge = ge;
		this.scanner = new Scanner(System.in);
	}
	
	/**
	 * Change the end message
	 * @param newMsg The new endmessage to show
	 */
	public void setEndMsg(final String newMsg)
	{
		this.endMsg = newMsg;
	}

	/**
	 * Ask the player for a draw
	 * @return Player's input
	 */
	public String askDraw()
	{
		System.out.println("\n" + this.ge.getCurrentPlayer() + " asked for a draw do you accept ? Y/N");
		return this.scanner.next();
	}
	
	/**
	 * Ask the player for the promotion
	 * @param pos The position of the promoted pawn
	 * @return Player's input
	 */
	public String askPromotion(final Position pos)
	{
		System.out.println("\n---------------------------");
		System.out.println("You can promote your pawn at " + pos + "\nWhich piece will it change to ? :\n"
				+ "N : Knight \nB : Bishop \nR : Rook \nQ : Queen");
		System.out.println("\n---------------------------");
		System.out.print("\nEnter your answer : ");
		return this.scanner.next();
	}
	
	/**
	 * Function to launch the game
	 */
	public void launch()
	{
		System.out.println("Enter the name of the white and the black player");
		this.ge.addPlayers(this.scanner.next(), this.scanner.next());
		
		this.loop();
	}
	
	/**
	 * Main loop
	 */
	private void loop()
	{
		String inputChoice;
		Position start;
		Position end;
		boolean play;

		// Main game loop
		while (this.ge.getState() == 0) {
			System.out.println(this.ge.turnInfo());

			// The player choose what he wants to do
			// If it's a wrong choice or just to display continue to loop
			play = false;
			while (!play) {
				System.out.println("------------------------------------------");
				System.out.println("What do you want to do (enter an answer) ? \n" + "1 : Check all the previous moves \n"
								+ "2 : Forfeit \n" + "3 : Ask for a draw \n" + "4 (or others) : Play");
				System.out.println("------------------------------------------\n");

				inputChoice = this.scanner.next();
				if (inputChoice.equals("1")) {
					System.out.println(this.ge.displayMoves());
				} else if (inputChoice.equals("2")) {
					this.endMsg= this.ge.forfeit();
					break;
				} else if (inputChoice.equals("3")) {
					if (this.ge.askForDraw()) {
						this.endMsg = "Both players agreed for a draw";
						break;
					}
				} else {
					play = true;
				}
			}
			if (this.ge.getState() != 0) {
				break;
			}

			// Ask the player to move a piece
			System.out.println("\n==> Enter which piece you choose and then where it goes");
			try {
				start = new Position(this.scanner.next());
				end = new Position(this.scanner.next());
			} catch (final IllegalArgumentException error) {
				System.out.println(error.getMessage());
				continue;
			}

			// Launch the turn
			try {
				this.ge.turn(start, end);
			} catch (final ChessMoveException error) {
				System.out.println(error.getMessage());
			}
		}
		
		System.out.println(this.ge.endInfo() + "\n" + this.endMsg);
		this.scanner.close();
		System.exit(0);
	}
}