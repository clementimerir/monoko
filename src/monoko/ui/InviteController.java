package monoko.ui;

import java.net.URL;
import java.util.ResourceBundle;

import monoko.objects.Game;
import monoko.utils.FxmlManager;
import monoko.utils.Manager;

public class InviteController extends InviteBase{
	private Game game;
	
	public InviteController(Game game) {
		this.game = game;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		_nameLabel.setText(game.getPlayer1().getName());
	}

	@Override
	public void onAcceptGameClicked() {
		try {
			Manager.getInstance().getNetwork().acceptGame(game);
			
			Manager.getInstance().getController().getRootAnchorPane().getChildren().set(0, new FxmlManager("./ui/game.fxml", new GameController2(game)).load());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
