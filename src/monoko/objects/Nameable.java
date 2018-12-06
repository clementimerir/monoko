package monoko.objects;

/**
 * Basic class for all objects that can have an ID and a NAME to simplify their creation
 * @author Bourdarie
 *
 */
public class Nameable {
	private int id;
	private String name;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
