package monoko;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import monoko.ui.MonokoController;
import monoko.utils.FxmlManager;
import monoko.utils.Manager;

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
		
		
		primaryStage.setOnHiding(new EventHandler<WindowEvent>() {

	         @Override
	         public void handle(WindowEvent event) {
	             Platform.runLater(new Runnable() {
	                 @Override
	                 public void run() {
	                     Manager.getInstance().getController().stopMusic();
	                     System.exit(0);
	                 }
	             });
	         }
	     });
	}

	@Override
	public void init() {
		//Initialize some stuff here (called automatically)
		System.out.println("Initialization...");
	}
	
	public static void main(String[] args) {
        launch(args);
    }
	
}
