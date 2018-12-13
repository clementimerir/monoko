package monoko;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import monoko.ui.MonokoController;
import monoko.utils.FxmlManager;

public class Monoko extends Application {
	
	public Monoko() {
		System.out.println("Constructing...");
	}

	@Override
	public void start(Stage primaryStage) {
		System.out.println("Starting...");
		primaryStage.setTitle("Monoko");
		Scene scene = new Scene( new FxmlManager("./ui/monoko.fxml", new MonokoController()).load() );
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		primaryStage.show();
	}

	@Override
	public void init() {
		//Initialize some stuff here (called automatically)
		System.out.println("Initialization...");
	}
	
}
