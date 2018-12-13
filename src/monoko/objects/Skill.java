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
	private boolean isPredilection = false;
	private int range;
	private int area;
	private int baseValue;
	private AttributesEnumType attributeConcerned;
	
	public Skill(int id, String name, SkillTypeEnum type, EffectTypeEnum effect) {
		setId(id);
		setName(name);
		setEffect(effect);
	}
	
	public Skill(int id, String name, SkillTypeEnum type, EffectTypeEnum effect, int price, boolean isPredilection) {
		setId(id);
		setName(name);
		setEffect(effect);
		setPrice(price);
		setIsPredilection(isPredilection);
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
	
	public boolean isPredilection() {
		return isPredilection;
	}

	public void setIsPredilection(boolean isPredilection) {
		this.isPredilection = isPredilection;
	}
}
