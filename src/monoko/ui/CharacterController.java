package monoko.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.image.Image;
import monoko.objects.Character;
import monoko.utils.FxmlManager;
import monoko.utils.Network;

public class CharacterController extends CharacterBase{

	private TeamEditorController _root;
	private Character _character;
	
	public CharacterController(TeamEditorController root, Character character) {
		_root = root;
		setCharacter(character);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		_nameLabel.setText(getCharacter().getName());
		_classImageview.setImage( new Image( new StringBuilder("/textures/").append(getCharacter().getJob().getName()).append("-Small").append(".png").toString() ) );
	}

	@Override
	public void onEditClicked() {
		_root.getRoot().getRootAnchorPane().getChildren().set(0, new FxmlManager("./ui/characterCreation.fxml", new CharacterEditorController(_root.getRoot(), _character)).load());
	}

	@Override
	public void onDeleteClicked() {
		_root.deleteCharacter(_character);
		
		try {
			new Network().deleteCharacter(_character);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	//SETTERS GETTERS
	public Character getCharacter() {
		return _character;
	}

	public void setCharacter(Character character) {
		this._character = character;
	}
}
