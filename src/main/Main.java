package main;

/**
 * Main class of the project
 * @author erwann
 *
 */
public class Main
{
	/** GameEngine of the project */
	private GameEngine ge;
	/** UserInterface of the project */
	private UserInterface ui;
	
	/**
	 * Main class to launch the game
	 */
	public Main()
	{
		this.ge = new GameEngine();
		this.ui = new UserInterface(this.ge);
		this.ge.setUI(this.ui);
		
		this.ui.launch();
	}
	
	/**
	 * main function
	 * @param args Args of the project (no need here)
	 */
	public static void main(String args[])
	{
		new Main();
	}
}