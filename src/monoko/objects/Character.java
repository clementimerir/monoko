package monoko.objects;

public class Character extends Nameable{
	private Attributes attributes;
	private Soul job;
	private Soul god;
	
	public Character(int id, String name, Soul job, Soul god) {
		setId(id);
		setName(name);
		setJob(job);
		setGod(god);
		buildAttributes(job,god);
	}
	
	/**
	 * Replaces setAttributes() setter => calculates automatically the character's attributes and prevent them from being lower than 0
	 * @param job the chosen job
	 * @param god the chosen god
	 */
	public void buildAttributes(Soul job, Soul god) {
		getAttributes().setHp( Math.max(0, job.getAttributes().getHp() + god.getAttributes().getHp() ) );
		getAttributes().setStrength( Math.max(0, job.getAttributes().getHp() + god.getAttributes().getHp() ) );
		getAttributes().setDexterity( Math.max(0, job.getAttributes().getHp() + god.getAttributes().getHp() ) );
		getAttributes().setIntelligence( Math.max(0, job.getAttributes().getHp() + god.getAttributes().getHp() ) );
		getAttributes().setSpeed( Math.max(0, job.getAttributes().getHp() + god.getAttributes().getHp() ) );
	}
	
	//GETTERS SETTERS
	public Attributes getAttributes() {
		return attributes;
	}

	public Soul getJob() {
		return job;
	}

	public void setJob(Soul job) {
		this.job = job;
	}

	public Soul getGod() {
		return god;
	}

	public void setGod(Soul god) {
		this.god = god;
	}
}
