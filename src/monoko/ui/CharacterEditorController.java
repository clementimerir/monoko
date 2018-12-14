package monoko.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import monoko.objects.Attributes;
import monoko.objects.Character;
import monoko.objects.Skill;
import monoko.objects.Soul;
import monoko.utils.FxmlManager;
import monoko.utils.SkillManager;

public class CharacterEditorController extends CharacterEditorBase{
	private Soul _job;
	private Soul _god;
	private Attributes _attributes;
	private SkillManager _skillManager;
	private List<Skill> _itemList;
	private int totalPrice;
	private boolean _addingPredilectionWeapon;
	private Skill _predilectionWeapon;
	
	/**
	 * Basic constructor used for character CREATION
	 */
	public CharacterEditorController() {
		init();
		
		_attributes = new Attributes(0, 0, 0, 0, 0);
		_job = new Soul(404, "None", new Attributes(0, 0, 0, 0, 0) );
		_god = new Soul(405, "None", new Attributes(0, 0, 0, 0, 0) );
	}
	
	/**
	 * Constructor used for EDITION instead of CREATION
	 * @param character The character to edit
	 */
	public CharacterEditorController(monoko.objects.Character character) {
		init();
		
		_attributes = character.getAttributes() == null ? new Attributes(0, 0, 0, 0, 0) : character.getAttributes();
		_job = character.getJob() == null ? new Soul(404, "None", new Attributes(0, 0, 0, 0, 0) ) : character.getJob();
		_god = character.getJob() == null ? new Soul(405, "None", new Attributes(0, 0, 0, 0, 0) ) : character.getGod();
		
		_jobComboBox.getSelectionModel().select(_job.getName());
		_godComboBox.getSelectionModel().select(_god.getName());
	}
	
	/**
	 * All the initialisations called BEFORE the UI is ready should be called here
	 */
	public void init() {
		_skillManager = new SkillManager();
		_itemList = new ArrayList<Skill>();
		_addingPredilectionWeapon = false;
		
		setTotalPrice(15);
	}
	
	/**
	 * All the initialisations called AFTER the UI is ready should be called here
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		populateComboBoxes();
		loadAttributes();

		_jobImageView.setFitHeight(_jobAnchorPane.widthProperty().doubleValue() * 0.9);
		_jobImageView.setFitWidth(_jobAnchorPane.widthProperty().doubleValue() * 0.9);
		_jobImageView.setX(-125);
		_jobImageView.setY( 125 );
		
		_godImageView.setFitHeight(_jobAnchorPane.widthProperty().doubleValue());
		_godImageView.setFitWidth(_jobAnchorPane.widthProperty().doubleValue());
//		_godImageView.setX(-125);
//		_godImageView.setY( 125 );

	}
	
	/**
	 * Put values in the combo boxes and add listeners
	 */
	private void populateComboBoxes() {
		_jobComboBox.getItems().addAll("Fighter", "Guardian", "Hunter", "Sage", "Cleric");
		_godComboBox.getItems().addAll("Ross'Fert", "Simmenoid", "Rey'Varb", "Rey'Telic", "Raeleh", "Prash", "Catarily", "Kreserber", "Rhena'Cen", "Sir'Aloe");
		_itemsComboBox.getItems().addAll("Sword", "Bow", "Tome:Pyromancy", "Scepter");
		
		_jobComboBox.valueProperty().addListener(new ChangeListener<String>() {
	        @SuppressWarnings("rawtypes")
			@Override
			public void changed(ObservableValue ov, String oldValue, String newValue) {
	        	if(oldValue == null || !oldValue.equals(newValue) ) {
	        		changeJobOrGod(1, newValue);
	        	}
	        }
	      });

		_godComboBox.valueProperty().addListener(new ChangeListener<String>() {
	        @SuppressWarnings("rawtypes")
			@Override
			public void changed(ObservableValue ov, String oldValue, String newValue) {
	        	if(oldValue == null || !oldValue.equals(newValue) ) {
	        		changeJobOrGod(2, newValue);
	        	}
	        }
	      });
		
		_itemsComboBox.valueProperty().addListener(new ChangeListener<String>() {
	        @SuppressWarnings("rawtypes")
			@Override
			public void changed(ObservableValue ov, String oldValue, String newValue) {
	        	addItem(newValue);
	        }
	      });
	}

	public boolean isEquipped(String name) {
		boolean isEquipped = false;
		
		if(_predilectionWeapon != null && _predilectionWeapon.getName().equals(name)) {
			return true;
		}
		
		for(Skill currentItem : _itemList) {
			if(currentItem.getName().equals(name)) {
				isEquipped = true;
			}
		}
		
		return isEquipped;
	}
	
	/**
	 * 
	 * @param name name of the item or weapon
	 */
	private void addItem(String name) {
		
		Skill itemChosen = _skillManager.getSkill(name);
		
		if(_addingPredilectionWeapon) {
			
			if( isEquipped(itemChosen.getName()) ) {
				for(Skill loop : _itemList) {
					if(loop.getName().equals(itemChosen.getName())) {
						_itemList.remove(loop);
						break;
					}
				}
				deleteItem(itemChosen);
			}
			
			_predilectionVBox.getChildren().clear();
			
			itemChosen.setId(0);
			itemChosen.setIsPredilection( _addingPredilectionWeapon );
			
			_predilectionWeapon = itemChosen;
			_predilectionVBox.getChildren().add( new FxmlManager("./ui/item.fxml", new ItemController(_predilectionWeapon, this)).load() );
			
		}else {
			if(!isEquipped(name)) {
				_itemsVBox.getChildren().clear();
				itemChosen.setId(_itemList.size());
				_itemList.add(itemChosen);
				setTotalPrice( getTotalPrice() + itemChosen.getPrice() );
				for (Skill loop : _itemList) {
					_itemsVBox.getChildren().add( new FxmlManager("./ui/item.fxml", new ItemController(loop, this)).load() );
				}				
			}else {
				Stage dialog = new Stage();
				Scene dialogScene = new Scene(new FxmlManager("./ui/warning.fxml", new WarningController("This item is already equipped.")).load(), 400, 100);
				dialog.setTitle("Login Error");
                dialog.setScene(dialogScene);
                dialog.show();
			}
		}
		
		loadAttributes();
		
	}
	
	public void deleteItem(Skill item) {
		
		if(item.isPredilection()) {
			_predilectionVBox.getChildren().clear();
			_predilectionWeapon = null;
		}else {

			_itemsVBox.getChildren().clear();
			_itemList.remove(item);
			setTotalPrice( getTotalPrice() - item.getPrice() );
			for(Skill skill : _itemList) {
				_itemsVBox.getChildren().add( new FxmlManager("./ui/item.fxml", new ItemController(skill, this)).load() );
			}
		}
		
		loadAttributes();
	}
	
	/**
	 * 
	 * @param i job change=1, god change=something else. 
	 * @param newValue
	 */
	private void changeJobOrGod(int i, String newValue) {
		
		Properties jobProperties = new Properties();
		try {
			jobProperties.load( new FileInputStream( new File("./res/data/jobAndGodAttributes.properties").getAbsolutePath() ) );
			int newHp = Integer.parseInt( jobProperties.getProperty( new StringBuilder(newValue).append(".").append("hp").toString() ) );
			int newStr = Integer.parseInt( jobProperties.getProperty( new StringBuilder(newValue).append(".").append("str").toString() ) );
			int newDex = Integer.parseInt( jobProperties.getProperty( new StringBuilder(newValue).append(".").append("dex").toString() ) );
			int newInt = Integer.parseInt( jobProperties.getProperty( new StringBuilder(newValue).append(".").append("int").toString() ) );
			int newSpd = Integer.parseInt( jobProperties.getProperty( new StringBuilder(newValue).append(".").append("spd").toString() ) );
			
			//if we are changing the job
			if(i == 1) {
				_addingPredilectionWeapon = true;

				_job = new Soul(1, newValue, new Attributes(newHp, newStr, newDex, newInt, newSpd));
				addItem( jobProperties.getProperty( new StringBuilder(newValue).append(".").append("weapon").toString()) );
				
				_addingPredilectionWeapon = false;
				
				_jobImageView.setImage( new Image( new StringBuilder("/textures/").append(newValue).append(".png").toString() ) );
			}else {
				_god = new Soul(1, newValue, new Attributes(newHp, newStr, newDex, newInt, newSpd));
				
				_godImageView.setImage( new Image( new StringBuilder("/textures/").append(newValue).append(".png").toString() ) );
				_godImageView.setFitWidth(291);
			}
			
			loadAttributes();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Recalculates and show the new attributes.
	 * Called whenever something changed : Job, god, item...
	 */
	public void loadAttributes() {
		_attributes.setAttributes(_job.getAttributes(), _god.getAttributes());

		_hpValueLabel.setText( String.valueOf(_attributes.getHp()) );
		_strValueLabel.setText( String.valueOf(_attributes.getStrength()) );
		_dexValueLabel.setText( String.valueOf(_attributes.getDexterity()) );
		_intValueLabel.setText( String.valueOf(_attributes.getIntelligence()) );
		_spdValueLabel.setText( String.valueOf(_attributes.getSpeed()) );
		_costValueLabel.setText( String.valueOf( getTotalPrice() ) );

		System.out.println("Attributes Reload finished");
	}
	
	@Override
	void onCreateCharacterButtonClicked() {
		
		Soul job = new Soul(_jobComboBox.getSelectionModel().getSelectedItem());
		Soul god = new Soul(_godComboBox.getSelectionModel().getSelectedItem());
		Character character = new Character(0, _nameTextfield.getText(), job, god, "", "");
		System.out.println(character);
	}
	
	//GETTERS SETTERS
	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public boolean isAddingPredilectionWeapon() {
		return _addingPredilectionWeapon;
	}

	public void setAddingPredilectionWeapon(boolean _addingPredilectionWeapon) {
		this._addingPredilectionWeapon = _addingPredilectionWeapon;
	}
}
