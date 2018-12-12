package monoko.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.layout.AnchorPane;
import monoko.utils.FxmlManager;

public class MonokoController extends MonokoBase{

	public MonokoController() {}
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		_rootAnchorPane.getChildren().add(new FxmlManager("./ui/mainMenu.fxml", new MainMenuController(this)).load());
	}

	@Override
	public AnchorPane getRootAnchorPane() {
		return _rootAnchorPane;
	}

	public void setRootAnchorPane(AnchorPane _rootAnchorPane) {
		this._rootAnchorPane = _rootAnchorPane;
	}
	
}
