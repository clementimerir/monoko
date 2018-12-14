package monoko.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import monoko.utils.FxmlManager;

public class LoginController extends LoginBase{

	MonokoController _parent;
	
	public LoginController(MonokoController parent) {
		setParent(parent);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		
	}

	@Override
	public void onLoginClicked() {
		//TODO Line below shouldn't be allowed by login failure
		_parent.getRootAnchorPane().getChildren().set(0, new FxmlManager("./ui/mainMenu.fxml", new MainMenuController(this.getParent())).load());
	}

	@Override
	public void onRegisterClicked() {
		//TODO
		//interface not made yet but OSEF
	}

	@Override
	public void onQuitClicked() {
		Platform.exit();		
	}

	//GETTERS SETTERS
	public MonokoController getParent() {
		return _parent;
	}

	public void setParent(MonokoController parent) {
		this._parent = parent;
	}
	
}
