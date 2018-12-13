package monoko.ui;

import java.net.URL;
import java.util.ResourceBundle;

import monoko.objects.Skill;

public class ItemController extends ItemBase{

	private CharacterEditorController _parent;
	private Skill _item;
	
	public ItemController(Skill item, CharacterEditorController parent) {
		setItem(item);
		setParent(parent);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		_nameLabel.setText(_item.getName());
		_deleteButton.setDisable( _item.isPredilection() );
	}

	//GETTERS SETTERS
	public Skill getItem() {
		return _item;
	}

	public void setItem(Skill item) {
		this._item = item;
	}

	@Override
	public void deleteItemButtonClicked() {
		getParent().deleteItem( getItem() );
	}
	
	public CharacterEditorController getParent() {
		return _parent;
	}

	public void setParent(CharacterEditorController parent) {
		this._parent = parent;
	}
}
