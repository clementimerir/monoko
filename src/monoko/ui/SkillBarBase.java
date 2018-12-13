package monoko.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToolBar;
import monoko.objects.Skill;

public abstract class SkillBarBase implements Initializable{
	@FXML ToolBar _skillToolBar;
	
	public abstract void addSkill(Skill skill);
}
