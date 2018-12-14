package monoko.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public abstract class LoginBase implements Initializable{
	@FXML TextField _idTextfield;
	@FXML PasswordField _passwordTextfield;
	
	@FXML Button _loginButton;
	@FXML Button _registerButton;
	@FXML Button _quitButton;
	
	@FXML public abstract void onLoginClicked();
	@FXML public abstract void onRegisterClicked();
	@FXML public abstract void onQuitClicked();
	
}
