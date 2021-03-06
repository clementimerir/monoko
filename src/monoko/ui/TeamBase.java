package monoko.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;

public abstract class TeamBase implements Initializable{
	@FXML TitledPane _teamTitledPane;
	@FXML HBox _teamsHBox;
	@FXML Button _deleteButton;
	
	@FXML public abstract void onDeleteClicked();
}
