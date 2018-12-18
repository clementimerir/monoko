package monoko.ui;

import java.net.URL;
import java.util.ResourceBundle;

public class TeamController extends TeamBase{

	private TeamEditorController _root;
	private String _name;
	
	public TeamController(TeamEditorController root, String name) {
		_root = root;
		_name = name;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		_teamTitledPane.setText(_name);
	}

}
