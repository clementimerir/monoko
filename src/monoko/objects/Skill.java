package monoko.objects;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * A weapon, a healing ability, a push/attract ability...etc.
 * @author Bourdarie
 *
 */
public class Skill extends Nameable{
	private SkillTypeEnum type;
	private EffectTypeEnum effect;
	private int price;
	private boolean isPredilection = false;
	private int range;
	private int area;
	private int baseValue;
	private float scaling;
	private AttributesEnumType attributeConcerned;
	private boolean inUse = false;

	public Skill(int id, String name, SkillTypeEnum type, EffectTypeEnum effect) {
		setId(id);
		setName(name);
		setEffect(effect);
		setPrice();
		setRange();
		setIsPredilection(false);
	}
	
	public Skill(int id, String name, SkillTypeEnum type, EffectTypeEnum effect, int price, boolean isPredilection) {
		setId(id);
		setName(name);
		setEffect(effect);
		setPrice(price);
		setRange();
		setIsPredilection(isPredilection);
	}
	
	public Skill(String name) {
		setName(name);
		Properties properties = new Properties();
		try {
			properties.load( new FileInputStream( new File("./res/data/jobAndGodAttributes.properties").getAbsolutePath() ) );
			int newHp = Integer.parseInt( properties.getProperty( new StringBuilder(name).append(".").append("hp").toString() ) );
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//GETTERS SETTERS
	public boolean isInUse() {
		return inUse;
	}

	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}	
	
	public SkillTypeEnum getType() {
		return type;
	}

	public void setType(SkillTypeEnum type) {
		this.type = type;
	}
	
	public EffectTypeEnum getEffect() {
		return effect;
	}

	public void setEffect(EffectTypeEnum effect) {
		this.effect = effect;
	}
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	public void setPrice() {
		Properties jobProperties = new Properties();
		try {
			jobProperties.load( new FileInputStream( new File("./res/data/jobAndGodAttributes.properties").getAbsolutePath() ) );
			int priceSkill = Integer.parseInt( jobProperties.getProperty( new StringBuilder(this.getName()).append(".").append("price").toString()));
			this.price = priceSkill;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.price = 0;
		}
		
	}
	
	public boolean isPredilection() {
		return isPredilection;
	}

	public void setIsPredilection(boolean isPredilection) {
		this.isPredilection = isPredilection;
	}
	
	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}
	
	//Set Range using the properties file
	public void setRange() {
		Properties jobProperties = new Properties();
		try {
			jobProperties.load( new FileInputStream( new File("./res/data/jobAndGodAttributes.properties").getAbsolutePath() ) );
			int rangeSkill = Integer.parseInt( jobProperties.getProperty( new StringBuilder(this.getName()).append(".").append("range").toString()));
			this.range = rangeSkill;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.range = 0;
		}
	}
	
	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}
	
	public int getBaseValue() {
		return baseValue;
	}

	public void setBaseValue(int baseValue) {
		this.baseValue = baseValue;
	}
	
	public AttributesEnumType getAttributeConcerned() {
		return attributeConcerned;
	}

	public void setAttributeConcerned(AttributesEnumType attributeConcerned) {
		this.attributeConcerned = attributeConcerned;
	}
	
	public float getScaling() {
		return scaling;
	}

	public void setScaling(float scaling) {
		this.scaling = scaling;
	}

}
