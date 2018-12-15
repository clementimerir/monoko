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
//		if(_idTextfield.getText().isEmpty() || _passwordTextfield.getText().isEmpty()) {
//			Stage dialog = new Stage();
//			Scene dialogScene = new Scene(new FxmlManager("./ui/warning.fxml", new WarningController("Please fill the ID and Password fields.")).load(), 400, 100);
//			dialog.setTitle("Warning");
//            dialog.setScene(dialogScene);
//            dialog.show();
//		}else {
//			
//			new Thread(new Runnable() {
//			    @Override public void run() {
//		        Platform.runLater(new Runnable() {
//		            @Override public void run() {
//		            	_loginProgressIndicator.setVisible(true);
//						_loginLabel.setVisible(true);
//		                _loginProgressIndicator.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
//		                
//						try {
//							Player player = new Network().login(_idTextfield.getText(), _passwordTextfield.getText());
//							if( player != null ) {
//								_parent.getRootAnchorPane().getChildren().set(0, new FxmlManager("./ui/mainMenu.fxml", new MainMenuController(_parent)).load());
//								_loginProgressIndicator.setVisible(false);
//								_loginLabel.setVisible(false);
//							}
//						} catch (Exception e) {
//							
//							new Thread(new Runnable() {
//							    @Override public void run() {
//						        Platform.runLater(new Runnable() {
//						            @Override public void run() {
//										_loginProgressIndicator.setVisible(false);
//										_loginLabel.setVisible(false);
//										
//										Stage dialog = new Stage();
//										Scene dialogScene = new Scene(new FxmlManager("./ui/warning.fxml", new WarningController("Login error: Check your ID and password.")).load(), 400, 100);
//										dialog.setTitle("Login Error");
//							            dialog.setScene(dialogScene);
//							            dialog.show();
//						            }
//						        });
//							}}).start();
//
//						} 
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
