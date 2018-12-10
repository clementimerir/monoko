package monoko.ui;

import javafx.application.Platform;

public class MainMenuController extends MainMenuBase{

	public MainMenuController() {
	}
	
	@Override
	void onPlayButtonClicked() {
		System.out.println("play");
	}

	@Override
	void onTeamEditorButtonClicked() {
		
	}

	@Override
	void onHelpButtonClicked() {
		
	}

	@Override
	void onLoreButtonClicked() {
		
	}
	
	@Override
	protected void onQuitButtonClicked() {
		Platform.exit();
	}

}
