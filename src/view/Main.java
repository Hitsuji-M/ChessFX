package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import controller.*;

public class Main extends Application
{
	private GameEngine engine;
	
	@Override
	public void start(Stage primaryStage) {
		this.engine = new GameEngine();
		
		/*
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
			Scene scene = new Scene(root);
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setTitle("ChessFX");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		*/
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
