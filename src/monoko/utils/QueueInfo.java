package monoko.utils;

import java.util.List;

public class QueueInfo {
	private List<String> waitingPlayers;
	private List<GameInfo> waitingGames;
	
	public QueueInfo(List<String> waitingPlayers, List<GameInfo> waitingGames) {
		setWaitingPlayers(waitingPlayers);
		setWaitingGames(waitingGames);
	}
	
	public List<String> getWaitingPlayers() {
		return waitingPlayers;
	}
	public void setWaitingPlayers(List<String> waitingPlayers) {
		this.waitingPlayers = waitingPlayers;
	}
	public List<GameInfo> getWaitingGames() {
		return waitingGames;
	}
	public void setWaitingGames(List<GameInfo> waitingGames) {
		this.waitingGames = waitingGames;
	}
}
