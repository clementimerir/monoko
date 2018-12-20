package monoko.ui;

import java.net.URL;
import java.util.ResourceBundle;

import monoko.utils.Manager;

public class ConnectedPlayerController extends ConnectedPlayerBase{
	private String player;
	
	public ConnectedPlayerController(String player) {
		this.player = player;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		_nameLabel.setText(player);
	}

	@Override
	public void onInviteClicked() {
		try {
			Manager.getInstance().getNetwork().invitePlayer(player);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
