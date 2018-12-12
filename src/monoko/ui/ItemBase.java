package monoko.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public abstract class ItemBase implements Initializable{
	@FXML Label _nameLabel;
	
	@FXML Button _deleteButton;
	
	public abstract void deleteItemButtonClicked();
}
