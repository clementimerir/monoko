package monoko.objects;

public class Soul extends Nameable{
	private Attributes attributes;
	
	public Soul(int id, String name, Attributes attributes) {
		setName(name);
		setAttributes(attributes);
		
	}
	
	//GETTERS SETTERS
	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}
	
}
