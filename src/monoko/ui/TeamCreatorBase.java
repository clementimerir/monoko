package monoko.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public abstract class TeamCreatorBase implements Initializable{
	@FXML TextField _nameTextfield;
	@FXML Button _okButton;
	
	@FXML public abstract void onOkClicked();
}
