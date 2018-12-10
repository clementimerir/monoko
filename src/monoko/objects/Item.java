package monoko.objects;

public class Item extends Nameable{

	private SkillTypeEnum type;
	private Effect effect;
	
	//CONSTRUCTOR
	public Item(int id, String name, SkillTypeEnum type, Effect effect) {
		setId(id);
		setName(name);
		setType(type);
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
