package monoko.utils;

import org.json.simple.JSONObject;

public class Action implements Comparable<Action> {
	private int characterID;
	private String playerName;
	private String skillName;
	private int posX;
	private int posY;
	private int id;

	public Action(int id, int characterID, String playerName, String skillName, int posX, int posY) {
		setId(id);
		setCharacterID(characterID);
		setPlayerName(playerName);
		setSkillName(skillName);
		setPosX(posX);
		setPosY(posY);
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJson() {
		JSONObject actionJSON = new JSONObject();
		actionJSON.put("id", getId());
		actionJSON.put("playerName", getPlayerName());
		actionJSON.put("characterID", getCharacterID());
		actionJSON.put("skillName", getSkillName());
		actionJSON.put("posX", getPosX());
		actionJSON.put("posY", getPosY());
		return actionJSON;
	}

	public int getCharacterID() {
		return characterID;
	}
	public void setCharacterID(int characterID) {
		this.characterID = characterID;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
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
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int compareTo(Action action) {
		return getId()-action.getId();
	}
}
