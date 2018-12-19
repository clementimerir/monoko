package monoko.objects;

import java.util.ArrayList;
import java.util.List;

public class User {
	private String username;
	private String password;
	private List<Character> characters;
	private List<Team> teams;
	
	public User() {
		setCharacters(new ArrayList<Character>());
		setTeams(new ArrayList<Team>());
	}
	
	public boolean addCharacter(Character c) {
		if(findCharacter(c.getId()) == null) {
			characters.add(c);
			return true;
		}
		return false;
	}
	
	public void deleteCharacter(int ref) {
		for(Character c : characters) {
			if(c.getId() == ref)
				characters.remove(c);
				break;
		}
	}
	
	public Character findCharacter(int ref) {
		for(Character c : characters) {
			if(c.getId() == ref)
				return c;
		}
		return null;
	}
	
	public boolean addTeam(Team t) {
		if(findTeam(t.getId()) == null) {
			teams.add(t);
			return true;
		}
		return false;
	}
	
	public void deleteTeam(int ref) {
		for(Team t : teams) {
			if(t.getId() == ref)
				teams.remove(t);
				break;
		}
	}
	
	public Team findTeam(int ref) {
		for(Team t : teams) {
			if(t.getId() == ref)
				return t;
		}
		return null;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Character> getCharacters() {
		return characters;
	}
	public void setCharacters(List<Character> characters) {
		this.characters = characters;
	}
	public List<Team> getTeams() {
		return teams;
	}
	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}
}
