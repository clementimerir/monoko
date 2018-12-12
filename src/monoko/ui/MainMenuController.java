package monoko.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import monoko.utils.FxmlManager;

public class MainMenuController extends MainMenuBase{

	private MonokoController _parent;
	
	public MainMenuController(MonokoController parent) {
		setParent(parent);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		
	}
	
	@Override
	void onPlayButtonClicked() {
//		_parent.getRootAnchorPane().getChildren().set(0, new FxmlManager("./ui/play.fxml").load());
	}

	@Override
	void onTeamEditorButtonClicked() {
		_parent.getRootAnchorPane().getChildren().set(0, new FxmlManager("./ui/characterCreation.fxml", new CharacterEditorController()).load());
	}

	@Override
	void onHelpButtonClicked() {
//		_parent.getRootAnchorPane().getChildren().set(0, new FxmlManager("./ui/help.fxml").load());
	}

	@Override
	void onLoreButtonClicked() {
//		_parent.getRootAnchorPane().getChildren().set(0, new FxmlManager("./ui/lore.fxml").load());
	}
	
	@Override
	protected void onQuitButtonClicked() {
		Platform.exit();
	}

	//GETTERS SETTERS
	public void setParent(MonokoController _parent) {
		this._parent = _parent;
	}
	
}
