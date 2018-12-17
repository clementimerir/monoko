package monoko.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public abstract class CharacterEditorBase implements Initializable{

	@FXML VBox _rootVBox;
	
	@FXML ComboBox<String> _jobComboBox;
	@FXML ComboBox<String> _godComboBox;
	@FXML ComboBox<String> _itemsComboBox;
	
	@FXML Button _jobInfoButton;
	@FXML Button _godInfoButton;
	@FXML Button _createCharacterButton;
	
	@FXML Label _titleLabel;
	@FXML Label _hpValueLabel;
	@FXML Label _strValueLabel;
	@FXML Label _dexValueLabel;
	@FXML Label _intValueLabel;
	@FXML Label _spdValueLabel;
	@FXML Label _costValueLabel;
	
	@FXML VBox _predilectionVBox;
	@FXML VBox _itemsVBox;
	
	@FXML ImageView _jobImageView;
	@FXML ImageView _godImageView;
	
	@FXML AnchorPane _jobAnchorPane;
	
	@FXML TextField _nameTextfield;
	
	@FXML abstract void onCreateCharacterButtonClicked();
	
}
