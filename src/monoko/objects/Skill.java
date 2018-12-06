package monoko.objects;

public class Skill extends Nameable{
	private SkillTypeEnum type;
	
	public Skill(int id, String name, SkillTypeEnum type) {
		setId(id);
		setName(name);
	}
	
	//GETTERS SETTERS
	public SkillTypeEnum getType() {
		return type;
	}

	public void setType(SkillTypeEnum type) {
		this.type = type;
	}
}
