package monoko.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import monoko.objects.Skill;
import monoko.utils.FxmlManager;

public class SkillBarController extends SkillBarBase{

	private List<Skill> _skills;
	
	public SkillBarController() {
		_skills = new ArrayList<Skill>();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		
	}

	@Override
	public void addSkill(Skill skill) {
		getSkills().add(skill);
		
	}

	/**
	 * hard reset and reload the skill bar
	 */
	public void loadSkills() {
		_skillToolBar.getItems().clear();
		for(Skill skill : _skills) {
			_skillToolBar.getItems().add( new FxmlManager("./ui/skill.fxml", new SkillController(skill)).load() );
		}
	}
	
	//GETTERS SETTERS
	public List<Skill> getSkills() {
		return _skills;
	}
	public void setSkills(List<Skill> skills) {
		this._skills = skills;
	}
}
