package monoko.ui;

import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import monoko.objects.Skill;

public abstract class SkillBarBase implements Initializable{
	@FXML HBox _skillBar;
	
	public abstract void addSkill(Skill skill);
	public abstract void addSkills(List<Skill> skills);
}
