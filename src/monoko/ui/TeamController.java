package monoko.ui;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import monoko.objects.Character;
import monoko.objects.Team;
import monoko.utils.FxmlManager;
import monoko.utils.Network;

public class TeamController extends TeamBase{

	private TeamEditorController _root;
	private Team _team;
	
	public TeamController(TeamEditorController root, Team team) {
		_root = root;
		setTeam(team);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		_teamTitledPane.setText(getTeam().getName());
		
		loadCharacters();
		
		_teamsHBox.setOnDragOver(new EventHandler<DragEvent>() {
		    public void handle(DragEvent event) {
		        if (event.getGestureSource() != _teamsHBox && event.getDragboard().hasString()) {
		            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
		        }
		        
		        event.consume();
		    }
		});
		
		_teamsHBox.setOnDragEntered(new EventHandler<DragEvent>() {
		    public void handle(DragEvent event) {
		             if (event.getGestureSource() != _teamsHBox && event.getDragboard().hasString()) {
		            	 _teamsHBox.setStyle("-fx-background-color: #5e4d41;");
		             }
		             event.consume();
		        }
		    });
		
		_teamsHBox.setOnDragExited(new EventHandler<DragEvent>() {
		    public void handle(DragEvent event) {
		    	_teamsHBox.setStyle("");
		        event.consume();
		    }
		});
		
		
		_teamsHBox.setOnDragDropped(new EventHandler<DragEvent>() {
		    public void handle(DragEvent event) {
		        Dragboard db = event.getDragboard();
		        boolean success = false;
		        if (db.hasString()) {
		        	for(Character currentCharacter : _root.getRoot().getUser().getCharacters()) {
		        		if(currentCharacter.getId() == Integer.valueOf(db.getString())) {

		        			Character chara = new Character(currentCharacter);
		        			chara.setId(ThreadLocalRandom.current().nextInt( 0 , 999999 + 1 ));
		        			chara.setTeam(_team);
		        			
		        			getTeam().getCharacters().add(chara);
		        			loadCharacters();
		        			
		        			try {
								new Network(_root.getRoot().getUser()).saveTeam(getTeam());
								new Network(_root.getRoot().getUser()).saveCharacter(chara);
							} catch (Exception e) {
								e.printStackTrace();
							}

		        			break;
		        			
		        		}
		        	}
		        	
		        	success = true;
		        }
		        
		        event.setDropCompleted(success);
		        event.consume();
		     }
		});
		
		_teamsHBox.setOnDragDone(new EventHandler<DragEvent>() {
		    public void handle(DragEvent event) {
		        if (event.getTransferMode() == TransferMode.MOVE) {

		        }
		        event.consume();
		    }
		});
		
	}

	private void loadCharacters() {
		_teamsHBox.getChildren().clear();
		for(Character character : getTeam().getCharacters()) {
			character.setTeam(_team);
			CharacterController controller = new CharacterController(_root, character, true);
			_teamsHBox.getChildren().add(new FxmlManager("./ui/character.fxml", controller).load());
		}
	}
	
	@Override
	public void onDeleteClicked() {
		try {
			new Network(_root.getRoot().getUser()).deleteTeam(_team);
			_root.getRoot().getUser().deleteTeam(this.getTeam().getId());
			_root.loadTeams();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//GETTERS SETTERS
	public Team getTeam() {
		return _team;
	}

	public void setTeam(Team team) {
		this._team = team;
	}

}
