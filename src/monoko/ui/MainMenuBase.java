package monoko.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public abstract class MainMenuBase extends Parent implements Initializable{

	@FXML VBox _mainMenuVBox;
	
	@FXML Label _titleLabel;
	
	@FXML Button _playButton;
	@FXML Button _teamEditorButton;
	@FXML Button _helpButton;
	@FXML Button _loreButton;
	@FXML Button _quitButton;
	
	@FXML abstract void onPlayButtonClicked();
	@FXML abstract void onTeamEditorButtonClicked();
	@FXML abstract void onHelpButtonClicked();
	@FXML abstract void onLoreButtonClicked();
	@FXML abstract void onQuitButtonClicked();
	
}
