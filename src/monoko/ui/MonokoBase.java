package monoko.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public abstract class MonokoBase implements Initializable{
	@FXML AnchorPane _rootAnchorPane;
	
	public abstract AnchorPane getRootAnchorPane();	
}
