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
	
	public Character(int id, String name, Soul job, Soul god, String inGameSprite, String inMenuSprite) {
		setId(id);
		setName(name);
		setJob(job);
		setGod(god);
		setAttributes(attributes);
		buildAttributes(job,god);
		setInGameSprite(inGameSprite);
		setInMenuSprite(inMenuSprite);
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
			getAttributes().setStrength( Math.max(0, job.getAttributes().getHp() + god.getAttributes().getHp() ) );
			getAttributes().setDexterity( Math.max(0, job.getAttributes().getHp() + god.getAttributes().getHp() ) );
			getAttributes().setIntelligence( Math.max(0, job.getAttributes().getHp() + god.getAttributes().getHp() ) );
			getAttributes().setSpeed( Math.max(0, job.getAttributes().getHp() + god.getAttributes().getHp() ) );
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

}
