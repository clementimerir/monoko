package monoko.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;

public abstract class LobbyBase implements Initializable{
	@FXML TitledPane _playersTitledPane;
	@FXML TitledPane _invitationsTitledPane;
	
	@FXML ComboBox<String> _teamsComboBox;
	
	@FXML Button _mainMenuButton;
	
	public abstract void onMainMenuClicked();	
}
