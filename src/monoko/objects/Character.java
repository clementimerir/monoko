package monoko.objects;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.json.JsonArray;
import javax.json.JsonObject;

import org.json.simple.*;

public class Character extends Nameable{
	private Attributes currentAttributes;
	private Attributes baseAttributes;
	private Soul job;
	private Soul god;
	private List<Skill> addedSkills;
	private List<Skill> allSkills;
	private String inGameSprite;
	private String inMenuSprite;
	private int posX;
	private int posY;
	private int vision = 0; // 0 = up || 1 = right || 2 = bas || 3 = left
	private boolean inUse;
	private Team team;
	private int cost;

	public Character(int id, String name, Soul job, Soul god, String inGame, String inMenu) {
		setId(id);
		setName(name);
		setJob(job);
		setGod(god);
		setBaseAttributes( new Attributes(0, 0, 0, 0, 0) );
		setCurrentAttributes( new Attributes(0, 0, 0, 0, 0) );
		buildAttributes(job,god);
		buildCurrentAttributes(job, god);
		setPosition(0, 0, 0);
		setInGameSprite(inGame);
		setInMenuSprite(inMenu);
		setInUse(false);
		allSkills = new ArrayList<Skill>();
	}

	public Character(int id, String name, Soul job, Soul god) {
		setId(id);
		setName(name);
		setJob(job);
		setGod(god);
		setBaseAttributes( new Attributes(0, 0, 0, 0, 0) );
		setCurrentAttributes( new Attributes(0, 0, 0, 0, 0) );
		buildAttributes(job,god);
		buildCurrentAttributes(job, god);
		setPosition(0, 0, 0);
		setInMenuSprite();
		setInGameSprite();
		setInUse(false);
		allSkills = new ArrayList<Skill>();
	}
	
	public Character(Character character) {
		setId(character.getId());
		setName(character.getName());
		setJob(character.getJob());
		setGod(character.getGod());
		setBaseAttributes(character.getBaseAttributes());
		setCurrentAttributes(character.getCurrentAttributes());
		buildAttributes(getJob(), getGod());
		buildCurrentAttributes(getJob(), getJob());
		setPosition(character.getPosX(), character.getPosY(), character.getVision());
		setInMenuSprite();
		setInGameSprite();
		setInUse(false);
		setSkills(character.getSkills());
	}
	
	public Character(JsonObject characterJson) {
		setId(characterJson.getInt("ref"));
		setName(characterJson.getString("name"));
		setPosition(0, 0, 0);
		setInUse(false);
		baseAttributes = new Attributes(0, 0, 0, 0, 0);
		currentAttributes = new Attributes(0, 0, 0, 0, 0);
		allSkills = new ArrayList<Skill>();
		addedSkills = new ArrayList<Skill>();
		String job = characterJson.getString("job");
		String god = characterJson.getString("god");
		if(!job.equals("none"))
			setJob(new Soul(job));
		if(!god.equals("none"))
			setGod(new Soul(god));
		setCost(characterJson.getInt("cost"));
		buildAllAttributes();
		setInGameSprite();
		setInMenuSprite();
		JsonArray skills = characterJson.getJsonArray("skills");
		for(int j=0; j<skills.size(); j++) {
			addSkill(new Skill(skills.getString(j)));
		}
		JsonArray addedSkills = characterJson.getJsonArray("addedSkills");
		for(int j=0; j<addedSkills.size(); j++) {
			addAddedSkill(new Skill(addedSkills.getString(j)));
		}
	}
	
	public List<Skill> getAddedSkills() {
		return addedSkills;
	}

	public void setAddedSkills(List<Skill> addedSkills) {
		this.addedSkills = addedSkills;
	}
	
	public int getVision() {
		return vision;
	}

	public void setVision(int vision) {
		this.vision = vision;
	}

	public void addSkill(Skill s) {
		allSkills.add(s);
	}

	public void addAddedSkill(Skill s) {
		addedSkills.add(s);
	}

	public void removeSkill(String skillName) {
		for(Skill s : allSkills) {
			if(s.getName() == skillName)
				allSkills.remove(s);
		}
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
	
	public boolean isUsingSkill() {
		for (int i = 0; i<this.getSkills().size(); i++) {
			if(this.getSkills().get(i).isInUse()) {
				return this.getSkills().get(i).isInUse();
			}
		}
		return false;
	}
	
	public Skill getUsedSkill() {
		for (int i = 0; i<this.getSkills().size(); i++) {
			if(this.getSkills().get(i).isInUse()) {
				return this.getSkills().get(i);
			}
		}
		return null;
	}
	
	public boolean setUsedSkill(Skill skill) {
		if(skill == null) {
			for (int i = 0; i<this.getSkills().size(); i++) {
				if(this.getSkills().get(i).isInUse()) {
					this.getSkills().get(i).setInUse(false);
					return true;
				}
			}
			return true;
		}
		
		for (int i = 0; i<this.getSkills().size(); i++) {
			if (this.getSkills().get(i).getName().equals(skill.getName())) {
				if(this.getSkills().get(i).isInUse()) {
					this.getSkills().get(i).setInUse(false);
					return false;
				}else {
					this.getSkills().get(i).setInUse(true);
					return true;
				}
			}
		}
		return false;
    }

	public int setDirection(int newX, int newY) {
		int oldPosX = this.getPosX();
		int oldPosY = this.getPosY();
		int newVision = 0;
		int i = oldPosX-newX;
		int j = oldPosY-newY;
		// 0 = up || 1 = right || 2 = bas || 3 = left
		if(j > 0) {
			//up
			newVision = 0;
		}else if(j < 0){
			//down
			newVision = 2;
		}else if(i > 0) {
			//left
			newVision = 3;
		}else if(i < 0){
			//right
			newVision = 1;
		}
		setVision(newVision);
		return newVision;
	}
	
	
	public void useSkill(Character target, Skill s, int posX, int posY) {
		
		switch(s.getEffect()) {
		case DAMAGE :
			dealDamage(target, s);
			break;
		case HEAL :
			heal(target, s);
			break;
		case DASH :
			setPosition(posX, posY, setDirection(posX,posY));
			break;
		case PUSH :
			int newPosX;
			int newPosY;
			int directionX;
			int directionY;
			directionX = (target.getPosX()>getPosX())?1:-1;
			newPosX = posX+s.getBaseValue()*directionX;
			directionY = (target.getPosY()>getPosY())?1:-1;
			newPosY = posY+s.getBaseValue()*directionY;
			setPosition(newPosX, newPosY, target.getVision());
			break;
		default:
			break;
		}
	}
	
	public void dealDamage(Character target, Skill s) {
		int modificator=0;
		switch(s.getAttributeConcerned()) {
		case STRENGTH:
			modificator=getCurrentAttributes().getStrength()*s.getScaling()/100;
			break;
		case DEXTERITY:
			modificator=getCurrentAttributes().getDexterity()*s.getScaling()/100;
			break;
		case INTELLIGENCE:
			modificator=getCurrentAttributes().getIntelligence()*s.getScaling()/100;
			break;
		default:
			break;
		}
		target.takeDamage(s.getBaseValue()+modificator);
	}

	public void heal(Character target, Skill s) {
		int modificator=0;
		switch(s.getAttributeConcerned()) {
		case INTELLIGENCE:
			modificator=getCurrentAttributes().getDexterity()*s.getScaling()/100;
			break;
		default:
			break;
		}
		target.getHealed(s.getBaseValue()+modificator);
	}
	
	public void moved(int speed) {
		this.getCurrentAttributes().setSpeed(this.getCurrentAttributes().getSpeed()-speed);
	}
	
	public void resetSpeed() {
		this.getCurrentAttributes().setSpeed(this.getBaseAttributes().getSpeed());
	}
	
	public void takeDamage(int damage) {
		getCurrentAttributes().setHp(getCurrentAttributes().getHp()-damage);
	}
	
	public void getHealed(int heal) {
		getCurrentAttributes().setHp(Math.min(getCurrentAttributes().getHp()+heal, getBaseAttributes().getHp()));
	}
	
	public void setPosition(int posX, int posY, int position) {
		setPosX(posX);
		setPosY(posY);
		if(position >= 0) {
			setVision(position);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJson(boolean... randomId) {
		boolean isRandomId = (boolean) (randomId.length > 0 ? randomId[0] : 0);
		JSONObject characterJSON = new JSONObject();
		JSONArray skillsJSON = new JSONArray();
		JSONArray addedSkillsJSON = new JSONArray();
		if(isRandomId) {
			Random random = new Random();
			characterJSON.put("ref", random.nextInt(1000000000));
		}
		else
			characterJSON.put("ref", getId());
		characterJSON.put("name", getName());
		characterJSON.put("cost", getCost());
		if (job!=null)
			characterJSON.put("job", job.getName());
		else
			characterJSON.put("job", "none");
		if (god!=null)
			characterJSON.put("god", god.getName());
		else
			characterJSON.put("god", "none");
		if (allSkills!=null) {
			for(int i=0; i<allSkills.size(); i++) {
				skillsJSON.add(allSkills.get(i).getName());
			}
		}
		if (addedSkills!=null) {
			for(int i=0; i<addedSkills.size(); i++) {
				addedSkillsJSON.add(addedSkills.get(i).getName());
			}
		}
		characterJSON.put("skills", skillsJSON);
		characterJSON.put("addedSkills", addedSkillsJSON);
		return characterJSON;
	}
	
	/**
	 * Replaces setAttributes() setter => calculates automatically the character's attributes and prevent them from being lower than 0
	 * @param job the chosen job
	 * @param god the chosen god
	 */
	public void buildAttributes(Soul job, Soul god) {
		if(job != null && god != null) {
			getBaseAttributes().setHp( Math.max(0, job.getAttributes().getHp() + god.getAttributes().getHp() ) );
			getBaseAttributes().setStrength( Math.max(0, job.getAttributes().getStrength() + god.getAttributes().getStrength() ) );
			getBaseAttributes().setDexterity( Math.max(0, job.getAttributes().getDexterity() + god.getAttributes().getDexterity() ) );
			getBaseAttributes().setIntelligence( Math.max(0, job.getAttributes().getIntelligence() + god.getAttributes().getIntelligence() ) );
			getBaseAttributes().setSpeed( Math.max(0, job.getAttributes().getSpeed() + god.getAttributes().getSpeed() ) );
		}
		else if(job != null) {
			getBaseAttributes().setHp( Math.max(0, job.getAttributes().getHp()) );
			getBaseAttributes().setStrength( Math.max(0, job.getAttributes().getStrength()) );
			getBaseAttributes().setDexterity( Math.max(0, job.getAttributes().getDexterity()) );
			getBaseAttributes().setIntelligence( Math.max(0, job.getAttributes().getIntelligence()) );
			getBaseAttributes().setSpeed( Math.max(0, job.getAttributes().getSpeed()) );
		}
		else {
			getBaseAttributes().setHp(0);
			getBaseAttributes().setStrength(0);
			getBaseAttributes().setDexterity(0);
			getBaseAttributes().setIntelligence(0);
			getBaseAttributes().setSpeed(0);
		}
	}
	
	public void buildCurrentAttributes(Soul job, Soul god) {
		getCurrentAttributes().setHp(getBaseAttributes().getHp());
		getCurrentAttributes().setStrength(getBaseAttributes().getStrength());
		getCurrentAttributes().setDexterity(getBaseAttributes().getDexterity());
		getCurrentAttributes().setIntelligence(getBaseAttributes().getIntelligence());
		getCurrentAttributes().setSpeed(getBaseAttributes().getSpeed());
	}
	
	public void buildAttributes() {
		buildAttributes(job, god);
	}
	
	public void buildCurrentAttributes() {
		buildCurrentAttributes(job, god);
	}
	
	public void buildAllAttributes() {
		buildAttributes(job, god);
		buildCurrentAttributes(job, god);
	}
	
	//GETTERS SETTERS
	public Attributes getCurrentAttributes() {
		return currentAttributes;
	}
	
	public void setCurrentAttributes(Attributes attributes) {
        this.currentAttributes = attributes;
    }
	
	public Attributes getBaseAttributes() {
		return baseAttributes;
	}
	
	public void setBaseAttributes(Attributes attributes) {
        this.baseAttributes = attributes;
    }
	
	public Soul getJob() {
		return job;
	}

	public void setJob(Soul job) {
		this.job = job;
	}

	public Soul getGod() {
		return god;
	}

	public void setGod(Soul god) {
		this.god = god;
	}
	
	public List<Skill> getSkills() {
		if(allSkills == null) {
			allSkills = new ArrayList<Skill>();
		}
		return allSkills;
	}

	public void setSkills(List<Skill> skills) {
		this.allSkills = skills;
	}
	
	public String getInGameSprite() {
		return inGameSprite;
	}

	public void setInGameSprite(String inGameSprite) {
		this.inGameSprite = inGameSprite;
	}
	
	public void setInGameSprite() {
		Properties jobProperties = new Properties();
		try {
			if(vision == 0) {
				jobProperties.load( new FileInputStream( new File("./res/data/jobAndGodAttributes.properties").getAbsolutePath() ) );
				this.inGameSprite = jobProperties.getProperty( new StringBuilder(this.getJob().getName()).append(".").append("up").toString());
			}else if(vision == 1) {
				jobProperties.load( new FileInputStream( new File("./res/data/jobAndGodAttributes.properties").getAbsolutePath() ) );
				this.inGameSprite = jobProperties.getProperty( new StringBuilder(this.getJob().getName()).append(".").append("right").toString());
			}else if(vision == 2) {
				jobProperties.load( new FileInputStream( new File("./res/data/jobAndGodAttributes.properties").getAbsolutePath() ) );
				this.inGameSprite = jobProperties.getProperty( new StringBuilder(this.getJob().getName()).append(".").append("down").toString());
			}else if(vision == 3) {
				jobProperties.load( new FileInputStream( new File("./res/data/jobAndGodAttributes.properties").getAbsolutePath() ) );
				this.inGameSprite = jobProperties.getProperty( new StringBuilder(this.getJob().getName()).append(".").append("left").toString());
			}else {
				jobProperties.load( new FileInputStream( new File("./res/data/jobAndGodAttributes.properties").getAbsolutePath() ) );
				this.inGameSprite = jobProperties.getProperty( new StringBuilder(this.getJob().getName()).append(".").append("up").toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public String getInMenuSprite() {
		return inMenuSprite;
	}

	public void setInMenuSprite(String inMenuSprite) {
		this.inMenuSprite = inMenuSprite;
	}
	
	public void setInMenuSprite() {
		Properties jobProperties = new Properties();
		try {
			jobProperties.load( new FileInputStream( new File("./res/data/jobAndGodAttributes.properties").getAbsolutePath() ) );
			this.inMenuSprite = jobProperties.getProperty( new StringBuilder(this.getJob().getName()).append(".").append("sprt").toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public boolean isInUse() {
		return inUse;
	}

	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}
	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

}
