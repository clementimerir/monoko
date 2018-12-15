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
	
	public Attributes(Attributes a) {
		setHp(a.getHp());
		setStrength(a.getStrength());
		setDexterity(a.getDexterity());
		setIntelligence(a.getIntelligence());
		setSpeed(a.getSpeed());
	}
	
	public void setAttributes(Attributes a, Attributes b) {
		setHp( Math.max(0, a.getHp()+b.getHp() ) );
		setStrength( Math.max(0, a.getStrength()+b.getStrength() ) );
		setDexterity( Math.max(0, a.getDexterity()+b.getDexterity() ) );
		setIntelligence( Math.max(0, a.getIntelligence()+b.getIntelligence() ) );
		setSpeed( Math.max(0, a.getSpeed()+b.getSpeed() ) );
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
