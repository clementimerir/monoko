package monoko.objects;

/**
 * A weapon, a healing ability, a push/attract ability...etc.
 * @author Bourdarie
 *
 */
public class Skill extends Nameable{
	private SkillTypeEnum type;
	private Effect effect;
	
	public Skill(int id, String name, SkillTypeEnum type, Effect effect) {
		setId(id);
		setName(name);
		setEffect(effect);
	}
	
	//GETTERS SETTERS
	public SkillTypeEnum getType() {
		return type;
	}

	public void setType(SkillTypeEnum type) {
		this.type = type;
	}
	public Effect getEffect() {
		return effect;
	}

	public void setEffect(Effect effect) {
		this.effect = effect;
	}
}
