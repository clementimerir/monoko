package monoko.utils;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import monoko.Monoko;

public class FxmlManager {
	Parent _node;
	public FxmlManager(String url, Initializable controller) {
		try {
			FXMLLoader loader = new FXMLLoader(Monoko.class.getResource(url));
			loader.setController(controller);
			_node = (Parent) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Parent load() {
		return _node;
	}
	
}
