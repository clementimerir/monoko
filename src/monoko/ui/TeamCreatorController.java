package monoko.ui;

import java.net.URL;
import java.util.ResourceBundle;

import monoko.utils.FxmlManager;

public class TeamCreatorController extends TeamCreatorBase{

	private TeamEditorController _root;
	
	public TeamCreatorController(TeamEditorController root) {
		_root = root;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		
	}

	@Override
	public void onOkClicked() {
		_root._teamsVBox.getChildren().add( new FxmlManager("./ui/team.fxml", new TeamController(_root, _nameTextfield.getText())).load() );
		_root._teamCreator.close();
	}

}
