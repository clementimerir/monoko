package monoko.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.Scene;
import javafx.stage.Stage;
import monoko.objects.Character;
import monoko.utils.FxmlManager;

public class TeamEditorController extends TeamEditorBase{

	private MonokoController _root;
	public Stage _teamCreator;
	
	public TeamEditorController(MonokoController root) {
		setRoot(root);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		new FxmlManager().fitToParent(_rootHBox, 0.0);
		loadCharacters();
	}

	@Override
	public void onCreateCharacterClicked() {
		_root.getRootAnchorPane().getChildren().set(0, new FxmlManager("./ui/characterCreation.fxml", new CharacterEditorController(_root)).load());
	}

	@Override
	public void onCreateTeamClicked() {
		Stage dialog = new Stage();
		Scene dialogScene = new Scene(new FxmlManager("./ui/teamCreator.fxml", new TeamCreatorController(this)).load(), 280, 50);
		dialog.setTitle("Team Creation");
        dialog.setScene(dialogScene);
        dialog.show();
        _teamCreator = dialog;
	}

	public void loadCharacters() {
		_charactersFlowpane.getChildren().clear();
		if(_root.getUser() != null) {
			for(Character character : _root.getUser().getCharacters()) {
				_charactersFlowpane.getChildren().add( new FxmlManager("./ui/character.fxml", new CharacterController(this, character)).load() );
			}			
		}
	}
	
	public void deleteCharacter(Character character) {
		getRoot().getUser().getCharacters().remove(character);
		loadCharacters();
	}
	
	//GETTERS SETTERS
	public MonokoController getRoot() {
		return _root;
	}

	public void setRoot(MonokoController _root) {
		this._root = _root;
	}

}
