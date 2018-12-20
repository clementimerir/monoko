package monoko.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public abstract class CharacterBase implements Initializable{
	
	@FXML HBox _rootHBox;
	@FXML ImageView _classImageview;
	@FXML ImageView _coinImageView;
	
	@FXML Label _nameLabel;
	@FXML Label _costLabel;

	@FXML Button _editButton;
	@FXML Button _deleteButton;
	
	@FXML VBox _buttonsVBox;
	
	@FXML public abstract void onEditClicked();
	@FXML public abstract void onDeleteClicked();
}
