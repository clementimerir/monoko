package monoko.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import monoko.utils.FxmlManager;
import monoko.utils.Manager;

public class MainMenuController extends MainMenuBase{

	public MainMenuController(MonokoController parent) {
		Manager.getInstance().setController(parent);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		new FxmlManager().fitToParent(_mainMenuVBox, 0.0);
		_loreButton.setText("Offline Game");
	}
	
	@Override
	void onPlayButtonClicked() {
//		Manager.getInstance().setGame( new GameController() );
//		Manager.getInstance().getController().getRootAnchorPane().getChildren().set(0, new FxmlManager("./ui/game.fxml", Manager.getInstance().getGame()).load());
		
		Manager.getInstance().setLobby( new LobbyController( Manager.getInstance().getController() ) );
		Manager.getInstance().getController().getRootAnchorPane().getChildren().set(0, new FxmlManager("./ui/lobby.fxml", Manager.getInstance().getLobby()).load());
	}

	@Override
	void onTeamEditorButtonClicked() {
		Manager.getInstance().setTeamEditor( new TeamEditorController(Manager.getInstance().getController()) );
		Manager.getInstance().getController().getRootAnchorPane().getChildren().set(0, new FxmlManager("./ui/teamEditor.fxml", Manager.getInstance().getTeamEditor()).load());
	}

	@Override
	void onHelpButtonClicked() {
//		_parent.getRootAnchorPane().getChildren().set(0, new FxmlManager("./ui/help.fxml").load());
	}

	@Override
	void onLoreButtonClicked() {
//		_parent.getRootAnchorPane().getChildren().set(0, new FxmlManager("./ui/lore.fxml").load());
		Manager.getInstance().setGame( new GameController() );
		Manager.getInstance().getController().getRootAnchorPane().getChildren().set(0, new FxmlManager("./ui/game.fxml", Manager.getInstance().getGame()).load());
	}
	
	@Override
	protected void onQuitButtonClicked() {
		Manager.getInstance().getController().stopMusic();
		Platform.exit();
	}

}
