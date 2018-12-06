package monoko.utils;

import monoko.objects.Character;
import monoko.objects.Skill;

/**
 * Singleton that manages skill effects
 * @author Bourdarie
 *
 */
public class SkillManager {

	private static SkillManager effectManager = new SkillManager();
	
	private SkillManager() {}
	
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
	
}
