package monoko.objects;

import java.util.ArrayList;
import java.util.List;

public class Team extends Nameable{
	private List<Character> characters;
	
	public Team(int id, String name, List<Character> characters) {
		setId(id);
		setName(name);
		setCharacters(characters);
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
