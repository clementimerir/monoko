package monoko;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Monoko extends Application {
	
	public Monoko() {
		System.out.println("Constructing...");
	}

	@Override
	public void start(Stage primaryStage) {
		System.out.println("Starting...");
		primaryStage.setTitle("Monoko");

		try {
			FXMLLoader loader = new FXMLLoader(Monoko.class.getResource("./ui/characterCreation.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
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
	
}
