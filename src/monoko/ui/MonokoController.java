package monoko.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.layout.AnchorPane;
import monoko.objects.Player;
import monoko.objects.User;
import monoko.utils.FxmlManager;

public class MonokoController extends MonokoBase{

	User _user;
	
	public MonokoController() {}
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		_rootAnchorPane.getChildren().add(new FxmlManager("./ui/login.fxml", new LoginController(this)).load());
	}

	@Override
	public AnchorPane getRootAnchorPane() {
		return _rootAnchorPane;
	}

	public void setRootAnchorPane(AnchorPane _rootAnchorPane) {
		this._rootAnchorPane = _rootAnchorPane;
	}
	
	//GETTERS SETTERS
	public User getUser() {
		return _user;
	}

	public void setUser(User player) {
		this._user = player;
	}
	
}
