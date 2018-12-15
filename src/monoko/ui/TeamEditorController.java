package monoko.ui;

import java.net.URL;
import java.util.ResourceBundle;

import monoko.utils.FxmlManager;

public class TeamEditorController extends TeamEditorBase{

	private MonokoController _root;
	
	public TeamEditorController(MonokoController root) {
		_root = root;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		new FxmlManager().fitToParent(_rootHBox, 0.0);
	}

	@Override
	public void onCreateCharacterClicked() {
		_root.getRootAnchorPane().getChildren().set(0, new FxmlManager("./ui/characterCreation.fxml", new CharacterEditorController(_root)).load());
	}

	@Override
	public void onCreateTeamClicked() {
		
	}
	
}
