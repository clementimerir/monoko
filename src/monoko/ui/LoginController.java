package monoko.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import monoko.objects.Player;
import monoko.utils.FxmlManager;
import monoko.utils.Network;

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
		try {
			
			if(_idTextfield.getText().isEmpty() || _passwordTextfield.getText().isEmpty()) {
				Stage dialog = new Stage();
				Scene dialogScene = new Scene(new FxmlManager("./ui/warning.fxml", new WarningController("Please fill the ID and Password fields.")).load(), 400, 100);
				dialog.setTitle("Warning");
                dialog.setScene(dialogScene);
                dialog.show();
			}else {
				Player player = new Network().login(_idTextfield.getText(), _passwordTextfield.getText()); 
				if( player != null ) {
					_parent.getRootAnchorPane().getChildren().set(0, new FxmlManager("./ui/mainMenu.fxml", new MainMenuController(this.getParent())).load());
				}
			}
			
		} catch (Exception e) {
			Stage dialog = new Stage();
			Scene dialogScene = new Scene(new FxmlManager("./ui/warning.fxml", new WarningController("Login error: Check your ID and password.")).load(), 400, 100);
			dialog.setTitle("Login Error");
            dialog.setScene(dialogScene);
            dialog.show();
			
		}
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
