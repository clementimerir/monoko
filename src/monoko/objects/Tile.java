package monoko.objects;

public class Tile{

	private int type;
	private Character c;
	private boolean selected;
	private boolean mvmnt;//boolean to check if a coloration for mouvement is needed

	public Tile(int type, Character c) {
		setType(type);
		setCharacter(c);
		setSelected(false);
		setMvmnt(false);
	}
	
	public boolean isNotObstacle(Tile tile) {
		if(type == 0 && !this.haveEnemyCharacter(tile)) {
			return true;
		}else {
			return false;
		}
		
		
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
	
	public boolean haveEnemyCharacter(Tile tile) {
		if (getCharacter() == null) {
			return false;
		}else if (this.getCharacter().getTeam().getId() == tile.getCharacter().getTeam().getId()){
			return false;
		}else {
			return true;
		}
	}
	
	
	public boolean isMvmnt() {
		return mvmnt;
	}

	public void setMvmnt(boolean mvmnt) {
		this.mvmnt = mvmnt;
	}

	
	public int getCharaMvmnt() {
		return this.getCharacter().getAttributes().getSpeed();
	}

	
}
