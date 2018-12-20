package monoko.utils;

import java.util.List;

import monoko.objects.Game;

public class QueueInfo {
	private List<String> waitingPlayers;
	private List<Game> waitingGames;
	
	public QueueInfo(List<String> waitingPlayers, List<Game> waitingGames) {
		setWaitingPlayers(waitingPlayers);
		setWaitingGames(waitingGames);
	}
	
	public List<String> getWaitingPlayers() {
		return waitingPlayers;
	}
	public void setWaitingPlayers(List<String> waitingPlayers) {
		this.waitingPlayers = waitingPlayers;
	}
	public List<Game> getWaitingGames() {
		return waitingGames;
	}
	public void setWaitingGames(List<Game> waitingGames) {
		this.waitingGames = waitingGames;
	}
}
