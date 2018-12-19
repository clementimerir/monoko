package monoko.utils;

import monoko.ui.GameController;
import monoko.ui.MainMenuController;
import monoko.ui.MonokoController;
import monoko.ui.TeamEditorController;

public class Manager {

	private static Manager _instance = new Manager();
	private static MonokoController _controller;
	private static MainMenuController _mainMenu;
	private static TeamEditorController _teamEditor;
	private static GameController _game;
	
	private Manager() {}
	
	//GETTERS SETTERS
	public static Manager getInstance() {
		return _instance;
	}
	public MonokoController getController() {
		return _controller;
	}

	public void setController(MonokoController controller) {
		Manager._controller = controller;
	}
	public MainMenuController getMainMenu() {
		return _mainMenu;
	}

	public void setMainMenu(MainMenuController mainMenu) {
		Manager._mainMenu = mainMenu;
	}
	public TeamEditorController getTeamEditor() {
		return _teamEditor;
	}

	public void setTeamEditor(TeamEditorController teamEditor) {
		Manager._teamEditor = teamEditor;
	}
	public GameController getGame() {
		return _game;
	}

	public void setGame(GameController game) {
		Manager._game = game;
	}

}
