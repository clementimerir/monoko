package monoko.ui;

import java.net.URL;
import java.util.ResourceBundle;

import monoko.objects.Character;

public class CharacterController extends CharacterBase{

	private TeamEditorController _root;
	private Character _character;
	
	public CharacterController(TeamEditorController root, Character character) {
		_root = root;
		setCharacter(character);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		
	}

	@Override
	public void onEditClicked() {
		//TODO
	}

	@Override
	public void onDeleteClicked() {
		_root.deleteCharacter(_character);
	}

	//SETTERS GETTERS
	public Character getCharacter() {
		return _character;
	}

	public void setCharacter(Character character) {
		this._character = character;
	}
}
