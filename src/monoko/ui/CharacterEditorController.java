package monoko.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

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
import monoko.utils.Manager;
import monoko.utils.Network;
import monoko.utils.SkillManager;

public class CharacterEditorController extends CharacterEditorBase{
	private MonokoController _root;
	private Soul _job;
	private Soul _god;
	private Attributes _attributes;
	private SkillManager _skillManager;
	private List<Skill> _addedItemList;
	private List<Skill> _itemList;
	private int totalPrice;
	private boolean _addingPredilectionWeapon;
	private Skill _predilectionWeapon;
	private boolean editionMode;
	private String _name;
	private int _id;
	
	/**
	 * Basic constructor used for character CREATION
	 */
	public CharacterEditorController(MonokoController root) {
		init();

		_root = root;
		
		_attributes = new Attributes(0, 0, 0, 0, 0);
		_job = new Soul(404, "None", new Attributes(0, 0, 0, 0, 0) );
		_god = new Soul(405, "None", new Attributes(0, 0, 0, 0, 0) );
	}
	
	/**
	 * Constructor used for EDITION instead of CREATION
	 * @param character The character to edit
	 */
	public CharacterEditorController(MonokoController root, monoko.objects.Character character) {
		init();
		
		_root = root;
		editionMode = true;
		
		_attributes = character.getBaseAttributes() == null ? new Attributes(0, 0, 0, 0, 0) : character.getBaseAttributes();
		_job = character.getJob() == null ? new Soul(404, "None", new Attributes(0, 0, 0, 0, 0) ) : character.getJob();
		_god = character.getJob() == null ? new Soul(405, "None", new Attributes(0, 0, 0, 0, 0) ) : character.getGod();
		_addedItemList = new ArrayList<Skill>(character.getAddedSkills());
		_name = character.getName();
		_id = character.getId();
	}
	
	/**
	 * All the initialisations called BEFORE the UI is ready should be called here
	 */
	public void init() {
		_skillManager = new SkillManager();
		_itemList = new ArrayList<Skill>();
		_addedItemList = new ArrayList<Skill>();
		_addingPredilectionWeapon = false;
		
		setTotalPrice(15);
	}
	
	/**
	 * All the initialisations called AFTER the UI is ready should be called here
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		new FxmlManager().fitToParent(_rootVBox, 0.0);
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

		
		if(editionMode) {
			_titleLabel.setText("Character Edition");
			_createCharacterButton.setText("Apply Changes");
			
			_jobComboBox.getSelectionModel().select(_job.getName());
			_godComboBox.getSelectionModel().select(_god.getName());
			_nameTextfield.setText(_name);
			
			for(Skill skill : _addedItemList) {
				
				_itemsVBox.getChildren().clear();
				skill.setId(_addedItemList.size());
				setTotalPrice( getTotalPrice() + skill.getPrice() );
				for (Skill loop : _addedItemList) {
					_itemsVBox.getChildren().add( new FxmlManager("./ui/item.fxml", new ItemController(loop, this)).load() );
				}
				
			}
		}
	}
	
	/**
	 * Put values in the combo boxes and add listeners
	 */
	private void populateComboBoxes() {
		_jobComboBox.getItems().addAll("Fighter", "Guardian", "Hunter", "Sage", "Cleric");
		_godComboBox.getItems().addAll("Ross'Fert", "Simmenoid", "Rey'Varb", "Rey'Telic", "Raeleh", "Prash", "Catarily", "Kreserber", "Rhena'Cen", "Sir'Aloe");
		_itemsComboBox.getItems().addAll("Sword", "Bow", "Pyromancy Tome", "Scepter");
		
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
		
		for(Skill currentItem : _addedItemList) {
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
//						_itemList.remove(loop);
						deleteItem(loop);
						break;
					}
				}
			}
			
			_predilectionVBox.getChildren().clear();
			
			itemChosen.setId(0);
			itemChosen.setIsPredilection( _addingPredilectionWeapon );
			
			_predilectionWeapon = itemChosen;
			_itemList.add(itemChosen);
			_predilectionVBox.getChildren().add( new FxmlManager("./ui/item.fxml", new ItemController(_predilectionWeapon, this)).load() );
			
		}else {
			if(!isEquipped(name)) {
				_itemsVBox.getChildren().clear();
				itemChosen.setId(_addedItemList.size());
				_addedItemList.add(itemChosen);
				_itemList.add(itemChosen);
				setTotalPrice( getTotalPrice() + itemChosen.getPrice() );
				for (Skill loop : _addedItemList) {
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
			_itemList.remove(_predilectionWeapon);
			_predilectionWeapon = null;
		}else {

			_itemsVBox.getChildren().clear();
			_addedItemList.remove(item);
			_itemList.remove(item);
			setTotalPrice( getTotalPrice() - item.getPrice() );
			for(Skill skill : _addedItemList) {
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
		Character character = new Character(ThreadLocalRandom.current().nextInt( 0 , 999999 + 1 ), _nameTextfield.getText(), job, god);
		character.setSkills(_itemList);
		character.setAddedSkills(_addedItemList);

		if(editionMode) {
			character.setId(_id);
			int i = 0;
			for(Character currentChar : _root.getUser().getCharacters()) {
				if(currentChar.getId() == _id) {
					_root.getUser().getCharacters().set(i, character);
				}
				i++;
			}
			
		}else {
			_root.getUser().getCharacters().add(character);
		}
		
		try {
			Network net = new Network();
			net.setUser(_root.getUser());
			net.saveCharacter(character);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		_root.getRootAnchorPane().getChildren().set(0, new FxmlManager("./ui/teamEditor.fxml", Manager.getInstance().getTeamEditor()).load());
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
