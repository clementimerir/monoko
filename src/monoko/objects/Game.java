package monoko.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import monoko.utils.Action;

public class Game extends Nameable{
	private Player player1;
	private Player player2;
	private String status;
	private String gameID;
	private List<Action> actions;
	private int lastActionID;
	
	public Game(Player player1, Player player2) {
		setPlayer1(player1);
		setPlayer2(player2);
		actions = new ArrayList<Action>();
		lastActionID = 0;
	}

	public void addAction(Action a) {
		actions.add(a);
		Collections.sort(actions);
	}
	
	//GETTERS SETTERS
	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public String getGameID() {
		return gameID;
	}

	public void setGameID(String gameID) {
		this.gameID = gameID;
	}
	
	public int getLastActionID() {
		return lastActionID;
	}

	public void setLastActionID(int lastActionID) {
		this.lastActionID = lastActionID;
	}
	
}
