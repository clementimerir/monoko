package monoko.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public abstract class CharacterBase implements Initializable{
	
	@FXML HBox _rootHBox;
	@FXML ImageView _classImageview;
	
	@FXML Label _nameLabel;

	@FXML Button _editButton;
	@FXML Button _deleteButton;
	
	@FXML public abstract void onEditClicked();
	@FXML public abstract void onDeleteClicked();
}
