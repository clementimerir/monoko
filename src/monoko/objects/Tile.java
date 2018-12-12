package monoko.objects;

public class Tile{

	private int type;
	private Character c;
	private boolean selected;

	public Tile(int type, Character c) {
		setType(type);
		setCharacter(c);
		setSelected(false);
	}
	
	public void setCharacter(Character c) {
		this.c = c;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public Character getCharacter() {
		return this.c;
	}
	
	public int getType() {
		return this.type;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public boolean haveCharacter() {
		if (getCharacter() == null) {
			return false;
		}else {
			return true;
		}
	}

	
}
