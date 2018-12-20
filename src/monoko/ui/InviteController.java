package monoko.ui;

import java.net.URL;
import java.util.ResourceBundle;

import monoko.objects.Game;
import monoko.utils.Manager;

public class InviteController extends InviteBase{
	private Game game;
	
	public InviteController(Game game) {
		this.game = game;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		_nameLabel.setText(game.getName());
	}

	@Override
	public void onAcceptGameClicked() {
		try {
			Manager.getInstance().getNetwork().acceptGame(game.getGameID());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
