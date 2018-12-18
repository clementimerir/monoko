package monoko.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import monoko.objects.Character;
import monoko.utils.FxmlManager;

public class TeamController extends TeamBase{

	private TeamEditorController _root;
	private String _name;
	
	public TeamController(TeamEditorController root, String name) {
		_root = root;
		_name = name;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		_teamTitledPane.setText(_name);
		
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
		        	System.out.println("AAA");
		        	for(Character currentCharacter : _root.getRoot().getUser().getCharacters()) {
		        		if(currentCharacter.getId() == Integer.valueOf(db.getString())) {
		        			
		        			_teamsHBox.getChildren().add(new FxmlManager("./ui/character.fxml", new CharacterController(_root, currentCharacter)).load());
		        			
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

}
