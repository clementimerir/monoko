package monoko.utils;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import monoko.Monoko;

public class FxmlManager {
	Parent _node;
	
	public FxmlManager() {}
	
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
	
	@SuppressWarnings("unused")
	public void fitToParent(Node node, double anchor) {
		AnchorPane.setBottomAnchor(node, anchor);
		AnchorPane.setTopAnchor(node, anchor);
		AnchorPane.setRightAnchor(node, anchor);
		AnchorPane.setLeftAnchor(node, anchor);		
	}
	
}
