package monoko.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public abstract class ConnectedPlayerBase implements Initializable{
	@FXML Label _nameLabel;
	@FXML Button _inviteButton;

	public abstract void onInviteClicked();	
}
