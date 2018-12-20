package monoko.utils;

public class Action {
	private int characterID;
	private int teamID;
	private String skillName;
	private int posX;
	private int posY;
	
	public Action(int characterID, int teamID, String skillName, int posX, int posY) {
		setCharacterID(characterID);
		setTeamID(teamID);
		setSkillName(skillName);
		setPosX(posX);
		setPosY(posY);
	}
	
	public int getCharacterID() {
		return characterID;
	}
	public void setCharacterID(int characterID) {
		this.characterID = characterID;
	}
	public int getTeamID() {
		return teamID;
	}
	public void setTeamID(int teamID) {
		this.teamID = teamID;
	}
	public String getSkillName() {
		return skillName;
	}
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
}
