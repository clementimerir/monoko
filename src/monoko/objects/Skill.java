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
	private int scaling;
	private AttributesEnumType attributeConcerned;
	private boolean inUse = false;

	public Skill(int id, String name) {
		setId(id);
		setName(name);
		setType();
		setEffect();
		setPrice();
		setRange();
		setIsPredilection(false);
		setArea();
		setBaseValue();
		setScaling();
		setAttributeConcerned();
	}
	
	public Skill(int id, String name, boolean isPredilection) {
		setId(id);
		setName(name);
		setType();
		setEffect();
		setPrice();
		setRange();
		setIsPredilection(isPredilection);
		setArea();
		setBaseValue();
		setScaling();
		setAttributeConcerned();
	}
	
	
	public Skill(int id, String name, SkillTypeEnum type, EffectTypeEnum effect) {
		setId(id);
		setName(name);
		setType();
		setEffect();
		setPrice();
		setRange();
		setIsPredilection(false);
		setArea();
		setBaseValue();
		setScaling();
		setAttributeConcerned();
	}
	
	public Skill(int id, String name, SkillTypeEnum type, EffectTypeEnum effect, int price, boolean isPredilection) {
		setId(id);
		setName(name);
		setType(type);
		setEffect(effect);
		setPrice(price);
		setRange();
		setIsPredilection(isPredilection);
		setArea();
		setBaseValue();
		setScaling();
		setAttributeConcerned();
	}
	
	public Skill(int id, String name, SkillTypeEnum type, EffectTypeEnum effect, int price, boolean isPredilection, int range, int area, int baseValue, int scaling, AttributesEnumType attributeConcerned) {
		setId(id);
		setName(name);
		setType(type);
		setEffect(effect);
		setPrice(price);
		setIsPredilection(isPredilection);
		setRange(range);
		setArea(area);
		setBaseValue(baseValue);
		setScaling(scaling);
		setAttributeConcerned(attributeConcerned);
	}
	
	public Skill(String name) {
		setName(name);
		Properties properties = new Properties();
		try {
			properties.load( new FileInputStream( new File("./res/data/jobAndGodAttributes.properties").getAbsolutePath() ) );
			setType(SkillTypeEnum.valueOf( properties.getProperty( new StringBuilder(name).append(".").append("type").toString() ) ) );
			setEffect(EffectTypeEnum.valueOf( properties.getProperty( new StringBuilder(name).append(".").append("effect").toString() ) ) );
			setPrice(Integer.parseInt( properties.getProperty( new StringBuilder(name).append(".").append("price").toString() ) ) );
			setRange(Integer.parseInt( properties.getProperty( new StringBuilder(name).append(".").append("range").toString() ) ) );
			setArea(Integer.parseInt( properties.getProperty( new StringBuilder(name).append(".").append("area").toString() ) ) );
			setBaseValue(Integer.parseInt( properties.getProperty( new StringBuilder(name).append(".").append("baseValue").toString() ) ) );
			setScaling(Integer.parseInt( properties.getProperty( new StringBuilder(name).append(".").append("scaling").toString() ) ) );
			setAttributeConcerned(AttributesEnumType.valueOf( properties.getProperty( new StringBuilder(name).append(".").append("attributeConcerned").toString() ) ) );
			
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
	
	public void setType() {
		Properties jobProperties = new Properties();
		try {
			jobProperties.load( new FileInputStream( new File("./res/data/jobAndGodAttributes.properties").getAbsolutePath() ) );
			String typeSkill = jobProperties.getProperty( new StringBuilder(this.getName()).append(".").append("type").toString());
			this.type = SkillTypeEnum.valueOf(typeSkill);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.type = null;
		}
	}
	
	public EffectTypeEnum getEffect() {
		return effect;
	}

	public void setEffect(EffectTypeEnum effect) {
		this.effect = effect;
	}
	
	public void setEffect() {
		Properties jobProperties = new Properties();
		try {
			jobProperties.load( new FileInputStream( new File("./res/data/jobAndGodAttributes.properties").getAbsolutePath() ) );
			String effectSkill = jobProperties.getProperty( new StringBuilder(this.getName()).append(".").append("effect").toString());
			this.effect = EffectTypeEnum.valueOf(effectSkill);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.effect = null;
		}
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
	
	public void setArea() {
		Properties jobProperties = new Properties();
		try {
			jobProperties.load( new FileInputStream( new File("./res/data/jobAndGodAttributes.properties").getAbsolutePath() ) );
			int areaSkill = Integer.parseInt( jobProperties.getProperty( new StringBuilder(this.getName()).append(".").append("area").toString()));
			this.area = areaSkill;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.area = 0;
		}
		
	}
	
	public int getBaseValue() {
		return baseValue;
	}

	public void setBaseValue(int baseValue) {
		this.baseValue = baseValue;
	}
	
	public void setBaseValue() {
		Properties jobProperties = new Properties();
		try {
			jobProperties.load( new FileInputStream( new File("./res/data/jobAndGodAttributes.properties").getAbsolutePath() ) );
			int baseValueSkill = Integer.parseInt( jobProperties.getProperty( new StringBuilder(this.getName()).append(".").append("baseValue").toString()));
			this.baseValue = baseValueSkill;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.baseValue = 0;
		}
	}
	
	public AttributesEnumType getAttributeConcerned() {
		return attributeConcerned;
	}

	public void setAttributeConcerned(AttributesEnumType attributeConcerned) {
		this.attributeConcerned = attributeConcerned;
	}
	
	public void setAttributeConcerned() {
		Properties jobProperties = new Properties();
		try {
			jobProperties.load( new FileInputStream( new File("./res/data/jobAndGodAttributes.properties").getAbsolutePath() ) );
			String attributeConcernedSkill = jobProperties.getProperty( new StringBuilder(this.getName()).append(".").append("attributeConcerned").toString());
			this.attributeConcerned = AttributesEnumType.valueOf(attributeConcernedSkill);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.attributeConcerned = null;
		}
	}
	
	public int getScaling() {
		return scaling;
	}

	public void setScaling(int scaling) {
		this.scaling = scaling;
	}
	
	public void setScaling() {
		Properties jobProperties = new Properties();
		try {
			jobProperties.load( new FileInputStream( new File("./res/data/jobAndGodAttributes.properties").getAbsolutePath() ) );
			int scalingSkill = Integer.parseInt( jobProperties.getProperty( new StringBuilder(this.getName()).append(".").append("scaling").toString()));
			this.scaling = scalingSkill;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.scaling = 0;
		}
	}

}
