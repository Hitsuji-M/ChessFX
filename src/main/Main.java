package main;

public class Main
{
	private GameEngine ge;
	private UserInterface ui;
	
	public Main()
	{
		this.ge = new GameEngine();
		this.ui = new UserInterface(this.ge);
		this.ge.setUI(this.ui);
		
		this.ui.launch();
	}
	
	public static void main(String args[])
	{
		new Main();
	}
}