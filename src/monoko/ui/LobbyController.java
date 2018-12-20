package monoko.ui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import monoko.objects.Game;
import monoko.objects.User;
import monoko.utils.FxmlManager;
import monoko.utils.Manager;
import monoko.utils.Network;
import monoko.utils.QueueInfo;

public class LobbyController extends LobbyBase{
	
	private MonokoController _root;
	private User _user;
	private Network _network;
	
	public LobbyController(MonokoController root) {
		_root = root;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		new FxmlManager().fitToParent(_rootVBox, 0.0);
		setUser( Manager.getInstance().getController().getUser() );
		setNetwork( Manager.getInstance().getNetwork() );
		refreshQueue();
	}

	@Override
	public void onMainMenuClicked() {
		_root.getRootAnchorPane().getChildren().set(0, new FxmlManager("./ui/mainMenu.fxml", Manager.getInstance().getMainMenu()).load());		
	}
	
	private void refreshQueue() {
		try {
			QueueInfo queue = getNetwork().updateQueue();
			refreshPlayers(queue.getWaitingPlayers());
			refreshGames(queue.getWaitingGames());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void refreshPlayers(List<String> players) {
		_playersVBox.getChildren().clear();
		for(String player : players) {
			_playersVBox.getChildren().add( new FxmlManager("./ui/connectedPlayer.fxml", new ConnectedPlayerController(player)).load() );
		}
	}
	
	private void refreshGames(List<Game> games) {
		_invitationVBox.getChildren().clear();
		for(Game game : games) {
			_invitationVBox.getChildren().add( new FxmlManager("./ui/connectedPlayer.fxml", new InviteController(game)).load() );
		}
	}

	//GETTERS SETTERS
	public User getUser() {
		return _user;
	}

	public void setUser(User user) {
		this._user = user;
	}
	public Network getNetwork() {
		return _network;
	}

	public void setNetwork(Network network) {
		this._network = network;
	}
}
