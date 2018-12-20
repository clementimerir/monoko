package monoko.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import monoko.objects.Skill;
import monoko.utils.FxmlManager;

public class SkillBarController2 extends SkillBarBase{

	private List<Skill> _skills;
	private GameController2 _game;
	
	public SkillBarController2(GameController2 game) {
		_skills = new ArrayList<Skill>();
		_game = game;
	}
	
	public SkillBarController2(GameController2 game, List<Skill> list) {
		_skills = list;
		_game = game;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		loadSkills();
	}

	@Override
	public void addSkill(Skill skill) {
		getSkills().add(skill);
	}
	
	@Override
	public void addSkills(List<Skill> skills) {
		setSkills(skills);
	}

	/**
	 * hard reset and reload the skill bar
	 */
	public void loadSkills() {
		_skillBar.getChildren().clear();
		for(Skill skill : _skills) {
			_skillBar.getChildren().add( new FxmlManager("./ui/skill.fxml", new SkillController2(_game, skill)).load() );
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
