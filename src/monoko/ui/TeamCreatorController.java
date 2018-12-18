package monoko.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

import monoko.objects.Team;
import monoko.utils.FxmlManager;
import monoko.utils.Network;

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
		
		try {
			new Network(_root.getRoot().getUser()).saveTeam(new Team(ThreadLocalRandom.current().nextInt( 0 , 999999 + 1 ), _nameTextfield.getText(), new ArrayList<monoko.objects.Character>()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		_root._teamCreator.close();
	}

}
