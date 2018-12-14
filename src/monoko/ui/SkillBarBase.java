package monoko.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import monoko.objects.Skill;

public abstract class SkillBarBase implements Initializable{
	@FXML HBox _skillBar;
	
	public abstract void addSkill(Skill skill);
}
