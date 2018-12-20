package monoko.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.StackPane;

public abstract class GameBase implements Initializable{
	/*@FXML VBox root;
	@FXML HBox gameHBox;*/
	
	@FXML StackPane root;
	@FXML ToolBar _skillsToolBar;
	@FXML ProgressIndicator _turnTimerIndicator;
	@FXML Label _vsLabel;
	@FXML Label _currentPlayerLabel;
}
