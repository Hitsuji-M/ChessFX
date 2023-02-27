package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class Controller
{
	@FXML
	private ImageView pizza;

	public void more(ActionEvent e)
	{
		pizza.setY(pizza.getY() - 5);
	}
	
	public void less(ActionEvent e)
	{
		pizza.setY(pizza.getY() + 5);
	}
}
