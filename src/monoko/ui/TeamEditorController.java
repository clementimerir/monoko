package monoko.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.Scene;
import javafx.stage.Stage;
import monoko.objects.Character;
import monoko.objects.Team;
import monoko.utils.FxmlManager;
import monoko.utils.Manager;

public class TeamEditorController extends TeamEditorBase{

	private MonokoController _root;
	public Stage _teamCreator;
	
	public TeamEditorController(MonokoController root) {
		setRoot(root);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		new FxmlManager().fitToParent(_rootVBox, 0.0);
		loadCharacters();
		loadTeams();
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
	
	@Override
	public void onMainMenuClicked() {
		_root.getRootAnchorPane().getChildren().set(0, new FxmlManager("./ui/mainMenu.fxml", Manager.getInstance().getMainMenu()).load());		
	}

	public void loadCharacters() {
		_charactersFlowpane.getChildren().clear();
		if(_root.getUser() != null) {
			for(Character character : _root.getUser().getCharacters()) {
				_charactersFlowpane.getChildren().add( new FxmlManager("./ui/character.fxml", new CharacterController(this, character, false)).load() );
			}			
		}
	}
	
	public void deleteCharacter(Character character) {
		getRoot().getUser().getCharacters().remove(character);
		loadCharacters();
	}
	
	public void loadTeams() {
		System.out.println("ss");
		_teamsVBox.getChildren().clear();
		if(_root.getUser() != null) {
			for(Team team : _root.getUser().getTeams()) {
				_teamsVBox.getChildren().add( new FxmlManager("./ui/team.fxml", new TeamController(this, team)).load() );
			}			
		}
	}
	
	public void deleteteam(Team team) {
		getRoot().getUser().getTeams().remove(team);
		loadTeams();
	}
	
	//GETTERS SETTERS
	public MonokoController getRoot() {
		return _root;
	}

	public void setRoot(MonokoController _root) {
		this._root = _root;
	}

}
