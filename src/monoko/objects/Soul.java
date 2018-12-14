package monoko.objects;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Job or God of a character
 * @author Bourdarie
 *
 */
public class Soul extends Nameable{
	private Attributes attributes;
	
	public Soul(int id, String name, Attributes attributes) {
		setName(name);
		setAttributes(attributes);
		
	}
	
	/**
	 * create a soul that directly have the right attributes
	 * @param name "Fighter", "Sir'Aloe"...etc.
	 */
	public Soul(String name) {
		setName(name);
		
		Properties jobProperties = new Properties();
		try {
			jobProperties.load( new FileInputStream( new File("./res/data/jobAndGodAttributes.properties").getAbsolutePath() ) );
			int newHp = Integer.parseInt( jobProperties.getProperty( new StringBuilder(name).append(".").append("hp").toString() ) );
			int newStr = Integer.parseInt( jobProperties.getProperty( new StringBuilder(name).append(".").append("str").toString() ) );
			int newDex = Integer.parseInt( jobProperties.getProperty( new StringBuilder(name).append(".").append("dex").toString() ) );
			int newInt = Integer.parseInt( jobProperties.getProperty( new StringBuilder(name).append(".").append("int").toString() ) );
			int newSpd = Integer.parseInt( jobProperties.getProperty( new StringBuilder(name).append(".").append("spd").toString() ) );
			
			setAttributes( new Attributes(newHp, newStr, newDex, newInt, newSpd) );
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//GETTERS SETTERS
	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}
	
}
