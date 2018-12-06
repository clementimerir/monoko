package monoko.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public abstract class MainMenuBase implements Initializable{

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
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {

	}

}
