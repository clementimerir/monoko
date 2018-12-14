package monoko.objects;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;

public class Character extends Nameable{
	private Attributes attributes;
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
		setAttributes( new Attributes(0, 0, 0, 0, 0) );
		buildAttributes(job,god);
		setInGameSprite(inGameSprite);
		setInMenuSprite(inMenuSprite);
		posX=0;
		posY=0;
		lookSouth=true;
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
			modificator=getAttributes().getStrength()/2;
			break;
		case DEXTERITY:
			modificator=getAttributes().getDexterity()/2;
			break;
		case INTELLIGENCE:
			modificator=getAttributes().getDexterity()/2;
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
			modificator=getAttributes().getDexterity()/2;
			break;
		default:
			break;
		}
		target.getHealed(s.getBaseValue()+modificator);
	}
	
	public void takeDamage(int damage) {
		getAttributes().setHp(getAttributes().getHp()-damage);
	}
	
	public void getHealed(int heal) {
		getAttributes().setHp(getAttributes().getHp()+heal);
	}
	
	public void setPosition(int posX, int posY, boolean lookSouth) {
		setPosX(posX);
		setPosY(posY);
		setLookSouth(lookSouth);
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJson() {
		JSONObject characterJSON = new JSONObject();
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
		System.out.print(characterJSON);
		return characterJSON;
	}
	
	/**
	 * Replaces setAttributes() setter => calculates automatically the character's attributes and prevent them from being lower than 0
	 * @param job the chosen job
	 * @param god the chosen god
	 */
	public void buildAttributes(Soul job, Soul god) {
		if(job != null) {
			getAttributes().setHp( Math.max(0, job.getAttributes().getHp() + god.getAttributes().getHp() ) );
			getAttributes().setStrength( Math.max(0, job.getAttributes().getStrength() + god.getAttributes().getStrength() ) );
			getAttributes().setDexterity( Math.max(0, job.getAttributes().getDexterity() + god.getAttributes().getDexterity() ) );
			getAttributes().setIntelligence( Math.max(0, job.getAttributes().getIntelligence() + god.getAttributes().getIntelligence() ) );
			getAttributes().setSpeed( Math.max(0, job.getAttributes().getSpeed() + god.getAttributes().getSpeed() ) );
		}
	}
	
	//GETTERS SETTERS
	public Attributes getAttributes() {
		return attributes;
	}
	public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
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
