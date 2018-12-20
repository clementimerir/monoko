package monoko.ui;

import java.net.URL;
import java.util.ResourceBundle;

import monoko.objects.User;
import monoko.utils.FxmlManager;
import monoko.utils.Manager;
import monoko.utils.Network;

public class LobbyController extends LobbyBase{
	
	private MonokoController _root;
	private User _user;
	private Network _network;
	
	public LobbyController(MonokoController root) {
		_root = root;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		setUser( Manager.getInstance().getController().getUser() );
		setNetwork( new Network(getUser()) );
	}

	@Override
	public void onMainMenuClicked() {
		_root.getRootAnchorPane().getChildren().set(0, new FxmlManager("./ui/mainMenu.fxml", Manager.getInstance().getMainMenu()).load());		
	}
	
	private void refresh() {
		
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
