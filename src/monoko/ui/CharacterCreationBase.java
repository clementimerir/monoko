package monoko.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public abstract class CharacterCreationBase implements Initializable{

	@FXML ComboBox<String> _jobComboBox;
	@FXML ComboBox<String> _godComboBox;
	@FXML ComboBox<String> _itemsComboBox;
	
	@FXML Button _jobInfoButton;
	@FXML Button _godInfoButton;
	@FXML Button _createCharacterButton;
	
	@FXML Label _hpValueLabel;
	@FXML Label _strValueLabel;
	@FXML Label _dexValueLabel;
	@FXML Label _intValueLabel;
	@FXML Label _spdValueLabel;
	@FXML Label _costValueLabel;
	
	@FXML abstract void onCreateCharacterButtonClicked();
	
}
