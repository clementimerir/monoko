package monoko.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.image.ImageView;
import monoko.objects.Skill;

public class SkillController extends SkillBase{

	private Skill _skill;
	private GameController _game;
	
	public SkillController(GameController game, Skill skill) {
		setSkill(skill);
		_game = game;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		
		_skillButton.setGraphic( new ImageView(  new StringBuilder("/skills/").append(_skill.getName()).append(".png").toString()  ) );
		
	}

	//GETTERS SETTERS
	public Skill getSkill() {
		return _skill;
	}

	public void setSkill(Skill skill) {
		this._skill = skill;
	}

	@Override
	public void onSkillClicked() {
		_game.setSelectedSkill(_skill);
	}
}
