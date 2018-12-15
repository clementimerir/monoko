package monoko.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public abstract class TeamEditorBase implements Initializable{
	@FXML HBox _rootHBox;
	
	@FXML FlowPane _charactersFlowpane;
	
	@FXML VBox _teamsVBox;

	@FXML Button _createCharacterButton;
	@FXML Button _createTeamButton;
	
	@FXML public abstract void onCreateCharacterClicked();
	@FXML public abstract void onCreateTeamClicked();
}
