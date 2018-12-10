package monoko.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import monoko.objects.Attributes;
import monoko.objects.Soul;

public class CharacterCreationController extends CharacterCreationBase{
	private Soul _job;
	private Soul _god;
	private Attributes _attributes;
	
	/**
	 * Basic constructor used for character CREATION
	 */
	public CharacterCreationController() {
		_attributes = new Attributes(0, 0, 0, 0, 0);
		_job = new Soul(404, "None", new Attributes(0, 0, 0, 0, 0) );
		_god = new Soul(405, "None", new Attributes(0, 0, 0, 0, 0) );
	}
	
	/**
	 * Constructor used for EDITION instead of CREATION.
	 * @param character The character to edit
	 */
	public CharacterCreationController(Character character) {
		//TODO LATER
	}
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		populateComboBoxes();
		loadAttributes();
	}
	
	/**
	 * Put values in the combo boxes and add listeners
	 */
	private void populateComboBoxes() {
		_jobComboBox.getItems().addAll("Fighter", "Guardian", "Hunter", "Sage", "Cleric");
		_godComboBox.getItems().addAll("Ross'Fert", "Simmenoid", "Rey'Varb", "Rey'Telic", "Raeleh", "Prash", "Catarily", "Kreserber", "Rhena'Cen", "Sir'Aloe");
		
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
	        	if( !oldValue.equals(newValue) ) {
	        		changeJobOrGod(2, newValue);
	        	}
	        }
	      });
	}
	
	private void changeJobOrGod(int i, String newValue) {
		
		Properties jobProperties = new Properties();
		try {
			jobProperties.load( new FileInputStream( new File("./res/data/jobAttributes.properties").getAbsolutePath() ) );
			int newHp = Integer.parseInt( jobProperties.getProperty( new StringBuilder(newValue).append(".").append("hp").toString() ) );
			int newStr = Integer.parseInt( jobProperties.getProperty( new StringBuilder(newValue).append(".").append("str").toString() ) );
			int newDex = Integer.parseInt( jobProperties.getProperty( new StringBuilder(newValue).append(".").append("dex").toString() ) );
			int newInt = Integer.parseInt( jobProperties.getProperty( new StringBuilder(newValue).append(".").append("int").toString() ) );
			int newSpd = Integer.parseInt( jobProperties.getProperty( new StringBuilder(newValue).append(".").append("spd").toString() ) );
			
			if(i == 1) {
				_job = new Soul(1, newValue, new Attributes(newHp, newStr, newDex, newInt, newSpd));			
			}else {
				_god = new Soul(1, newValue, new Attributes(newHp, newStr, newDex, newInt, newSpd));
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

		System.out.println("Attributes Reload finished");
	}
	
	@Override
	void onCreateCharacterButtonClicked() {
		//TODO LATER
		System.out.println("create button clicked");
	}
	
}
