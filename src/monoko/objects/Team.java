package monoko.objects;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Team extends Nameable{
	private List<Character> characters;
	
	public Team(int id, String name, List<Character> characters) {
		setId(id);
		setName(name);
		setCharacters(characters);
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJson() {
		JSONArray charactersRefs = new JSONArray();
		JSONObject teamJSON = new JSONObject();
		teamJSON.put("ref", getId());
		teamJSON.put("name", getName());
		for(int i=0; i<characters.size(); i++) {
			charactersRefs.add(characters.get(i).getId());
		}
		teamJSON.put("charactersRefs", charactersRefs);
		return teamJSON;
	}
	
	//GETTERS SETTERS
	public List<Character> getCharacters() {
		if(characters == null) {
			characters = new ArrayList<Character>();
		}
		return characters;
	}

	public void setCharacters(List<Character> characters) {
		this.characters = characters;
	}
}
