package monoko.objects;

/**
 * A weapon, a healing ability, a push/attract ability...etc.
 * @author Bourdarie
 *
 */
public class Skill extends Nameable{
	private SkillTypeEnum type;
	private EffectTypeEnum effect;
	private int price;
	
	public Skill(int id, String name, SkillTypeEnum type, EffectTypeEnum effect) {
		setId(id);
		setName(name);
		setEffect(effect);
	}
	
	public Skill(int id, String name, SkillTypeEnum type, EffectTypeEnum effect, int price) {
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
}
