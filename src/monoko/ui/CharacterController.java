package monoko.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import monoko.objects.Character;
import monoko.objects.Team;
import monoko.utils.FxmlManager;
import monoko.utils.Network;

public class CharacterController extends CharacterBase{

	private TeamEditorController _root;
	private Character _character;
	private boolean _teamMode;
	private Team _team;
	
	public CharacterController(TeamEditorController root, Character character, boolean teamMode) {
		_root = root;
		setCharacter(character);
		_teamMode = teamMode;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		_nameLabel.setText(getCharacter().getName());
		_classImageview.setImage( new Image( new StringBuilder("/textures/").append(getCharacter().getJob().getName()).append("-Small").append(".png").toString() ) );
//		_editButton.setVisible(!_teamMode);
		if(_teamMode) {
			_buttonsVBox.getChildren().remove(_editButton);
		}
		
		_rootHBox.setOnDragDetected(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				Dragboard db = _rootHBox.startDragAndDrop(TransferMode.ANY);
				ClipboardContent content = new ClipboardContent();
				content.putString(String.valueOf( _character.getId()) );
				db.setContent(content);
				event.consume();
			}
		});
		
	}

	@Override
	public void onEditClicked() {
		_root.getRoot().getRootAnchorPane().getChildren().set(0, new FxmlManager("./ui/characterCreation.fxml", new CharacterEditorController(_root.getRoot(), _character)).load());
	}

	@Override
	public void onDeleteClicked() {
		if(_teamMode) {
			
			getTeam().getCharacters().remove(_character);
			
			try {
				Network net = new Network(_root.getRoot().getUser());
				net.saveTeam(getTeam());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			_root.loadTeams();
		}else {
			_root.deleteCharacter(_character);
			
			try {
				Network net = new Network(_root.getRoot().getUser());
				net.deleteCharacter(_character);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	//SETTERS GETTERS
	public Character getCharacter() {
		return _character;
	}

	public void setCharacter(Character character) {
		this._character = character;
	}
	public Team getTeam() {
		return _team;
	}

	public void setTeam(Team team) {
		this._team = team;
	}
}
