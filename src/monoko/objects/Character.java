package monoko.objects;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.*;

public class Character extends Nameable{
	private Attributes currentAttributes;
	private Attributes baseAttributes;
	private Soul job;
	private Soul god;
	private List<Skill> skills;
	private String inGameSprite;
	private String inMenuSprite;
	private int posX;
	private int posY;
	private boolean lookSouth;
	private Team team;


	public Character(int id, String name, Soul job, Soul god, String inGameSprite, String inMenuSprite) {
		setId(id);
		setName(name);
		setJob(job);
		setGod(god);
		setBaseAttributes( new Attributes(0, 0, 0, 0, 0) );
		setCurrentAttributes( new Attributes(0, 0, 0, 0, 0) );
		buildAttributes(job,god);
		buildCurrentAttributes(job, god);
		setInGameSprite(inGameSprite);
		setInMenuSprite(inMenuSprite);
		setPosition(0, 0, true);
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
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
			setPosition(posX, posY, getPosY()>posY);
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
			target.setPosition(newPosX, newPosY, target.getLookSouth());
			break;
		default:
			break;
		}
	}
	
	public void dealDamage(Character target, Skill s) {
		int modificator=0;
		switch(s.getAttributeConcerned()) {
		case STRENGTH:
			modificator=getCurrentAttributes().getStrength()/2;
			break;
		case DEXTERITY:
			modificator=getCurrentAttributes().getDexterity()/2;
			break;
		case INTELLIGENCE:
			modificator=getCurrentAttributes().getDexterity()/2;
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
			modificator=getCurrentAttributes().getDexterity()/2;
			break;
		default:
			break;
		}
		target.getHealed(s.getBaseValue()+modificator);
	}
	
	public void takeDamage(int damage) {
		getCurrentAttributes().setHp(getCurrentAttributes().getHp()-damage);
	}
	
	public void getHealed(int heal) {
		getCurrentAttributes().setHp(Math.min(getCurrentAttributes().getHp()+heal, getBaseAttributes().getHp()));
	}
	
	public void setPosition(int posX, int posY, boolean lookSouth) {
		setPosX(posX);
		setPosY(posY);
		setLookSouth(lookSouth);
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJson() {
		JSONObject characterJSON = new JSONObject();
		JSONArray skillsJSON = new JSONArray();
		characterJSON.put("ref", getId());
		characterJSON.put("name", getName());
		if (job!=null)
			characterJSON.put("job", job.getName());
		else
			characterJSON.put("job", "none");
		if (god!=null)
			characterJSON.put("god", god.getName());
		else
			characterJSON.put("god", "none");
		if (skills!=null) {
			for(int i=0; i<skills.size(); i++) {
				skillsJSON.add(skills.get(i).getName());
			}
		}
		characterJSON.put("skills", skillsJSON);
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
		if(skills == null) {
			skills = new ArrayList<Skill>();
		}
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}
	
	public String getInGameSprite() {
		return inGameSprite;
	}

	public void setInGameSprite(String inGameSprite) {
		this.inGameSprite = inGameSprite;
	}

	public String getInMenuSprite() {
		return inMenuSprite;
	}

	public void setInMenuSprite(String inMenuSprite) {
		this.inMenuSprite = inMenuSprite;
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

	public boolean getLookSouth() {
		return lookSouth;
	}

	public void setLookSouth(boolean lookSouth) {
		this.lookSouth = lookSouth;
	}


}
