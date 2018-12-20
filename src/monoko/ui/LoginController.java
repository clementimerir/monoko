package monoko.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import monoko.objects.User;
import monoko.utils.FxmlManager;
import monoko.utils.Manager;
import monoko.utils.Network;

public class LoginController extends LoginBase{

	MonokoController _parent;
	
	public LoginController(MonokoController parent) {
		setParent(parent);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
//		onLoginClicked();
		Manager.getInstance().setController(_parent);
	}

	@Override
	public void onLoginClicked() {
//		if(_idTextfield.getText().isEmpty() || _passwordTextfield.getText().isEmpty()) {
//			Stage dialog = new Stage();
//			Scene dialogScene = new Scene(new FxmlManager("./ui/warning.fxml", new WarningController("Please fill the ID and Password fields.")).load(), 400, 100);
//			dialog.setTitle("Warning");
//            dialog.setScene(dialogScene);
//            dialog.show();
//		}else {
			
//			new Thread(new Runnable() {
//			    @Override public void run() {
//		        Platform.runLater(new Runnable() {
//		            @Override public void run() {
		            	_loginProgressIndicator.setVisible(true);
						_loginLabel.setVisible(true);
		                _loginProgressIndicator.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
		                
						try {
							User user = new Network().login("p", "p");
//							User user = new Network().login(_idTextfield.getText(), _passwordTextfield.getText());
//							if( user != null ) {
								_parent.setUser(user);
								Manager.getInstance().setMainMenu(new MainMenuController(Manager.getInstance().getController()));
								_parent.getRootAnchorPane().getChildren().set(0, new FxmlManager("./ui/mainMenu.fxml", Manager.getInstance().getMainMenu()).load());
								_loginProgressIndicator.setVisible(false);
								_loginLabel.setVisible(false);
//							}
						} catch (Exception e) {
							e.printStackTrace();
							new Thread(new Runnable() {
							    @Override public void run() {
						        Platform.runLater(new Runnable() {
						            @Override public void run() {
										_loginProgressIndicator.setVisible(false);
										_loginLabel.setVisible(false);
										
										Stage dialog = new Stage();
										Scene dialogScene = new Scene(new FxmlManager("./ui/warning.fxml", new WarningController("Login error: Check your ID and password.")).load(), 400, 100);
										dialog.setTitle("Login Error");
							            dialog.setScene(dialogScene);
							            dialog.show();
						            }
						        });
							}}).start();

						} 
//		            }
//		        });
//			   }
//			}).start();
//		}
		
		_parent.getRootAnchorPane().getChildren().set(0, new FxmlManager("./ui/mainMenu.fxml", new MainMenuController(_parent)).load());
	}

	@Override
	public void onRegisterClicked() {
		//TODO
		//interface not made yet but OSEF
	}

	@Override
	public void onQuitClicked() {
		Manager.getInstance().getController().stopMusic();
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
