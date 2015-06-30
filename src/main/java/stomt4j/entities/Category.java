package stomt4j.entities;

public class Category {
	
	private String id;
	private String displayname;
	
	public Category(String id, String displayname) {
		this.id = id;
		this.displayname = displayname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}
	
}
