package monoko.utils;

import monoko.objects.Character;
import monoko.objects.EffectTypeEnum;
import monoko.objects.Skill;
import monoko.objects.SkillTypeEnum;

/**
 * Manages skill effects
 * TODO should be a singleton but idk
 * @author Bourdarie
 *
 */
public class SkillManager {

	private static SkillManager effectManager = new SkillManager();
	
	public SkillManager() {}
	
	public static SkillManager getInstance( ) {
		return effectManager;
	}
	
	/**
	 * Interpret and applies the effect casted by the source on the target
	 * @param source Caster of the skill effect
	 * @param target Receiver of the skill effect
	 * @param skill Attack, heal, dash, push, attract...etc.
	 */
	protected void applySkill(Character source, Character target, Skill skill) {
		System.out.println("Applying efefct lol");
	}
	
	
	public Skill getSkill(String name) {
		switch (name) {
		case "Sword":
			return new Skill(0, name, SkillTypeEnum.OFFENSE, EffectTypeEnum.DAMAGE, 10, false);
		case "Bow":
			return new Skill(1, name, SkillTypeEnum.OFFENSE, EffectTypeEnum.DAMAGE, 10, false);
		case "Tome:Pyromancy":
			return new Skill(3, name, SkillTypeEnum.OFFENSE, EffectTypeEnum.DAMAGE, 10, false);
		case "Scepter":
			return new Skill(4, name, SkillTypeEnum.SUPPORT, EffectTypeEnum.DAMAGE, 10, false);
		default:break;
		}
		return null;
	}
}
