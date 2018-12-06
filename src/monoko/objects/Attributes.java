package monoko.objects;

public class Attributes {

	private int hp;
	private int strength;
	private int dexterity;
	private int intelligence;
	private int speed;
	
	public Attributes(int hp, int strength, int dexterity, int intelligence, int speed) {
		setHp(hp);
		setStrength(strength);
		setDexterity(dexterity);
		setIntelligence(intelligence);
		setSpeed(speed);
	}
	
	//GETTERS SETTERS
	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getDexterity() {
		return dexterity;
	}

	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
}
