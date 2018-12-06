package monoko.objects;

public class Player extends Nameable{
	private Team team;

	//CONSTRUCTOR
	public Player(int id, String name, Team team) {
		setId(id);
		setName(name);
		setTeam(team);
	}
	
	//GETTERS SETTERS
	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
}
