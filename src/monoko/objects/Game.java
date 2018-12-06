package monoko.objects;

public class Game extends Nameable{
	private Player player1;
	private Player player2;
	
	public Game(Player player1, Player player2) {
		setPlayer1(player1);
		setPlayer2(player2);
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
	
}
