package monoko;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Monoko extends Application {
	
	public Monoko() {
		//super();
		
		System.out.println("Constructing...");
	}

	@Override
	public void start(Stage primaryStage) {
		System.out.println("Starting...");
		primaryStage.setTitle("Monoko");
		
		try {
			FXMLLoader loader = new FXMLLoader(Monoko.class.getResource("mainMenu.fxml"));
			BorderPane page = (BorderPane) loader.load();
			Scene scene = new Scene(page);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init() {
		//Initialize some stuff here (called automatically)
		System.out.println("Initialization...");
	}
	
	/*public static void main(String[] args) {
		launch(args);
	}*/
}