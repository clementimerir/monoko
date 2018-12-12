package monoko.objects;

/**
 * A weapon, a healing ability, a push/attract ability...etc.
 * @author Bourdarie
 *
 */
public class Skill extends Nameable{
	private SkillTypeEnum type;
	private Effect effect;
	private int price;
	
	public Skill(int id, String name, SkillTypeEnum type, Effect effect) {
		setId(id);
		setName(name);
		setEffect(effect);
	}
	
	public Skill(int id, String name, SkillTypeEnum type, Effect effect, int price) {
		setId(id);
		setName(name);
		setEffect(effect);
		setPrice(price);
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
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}
