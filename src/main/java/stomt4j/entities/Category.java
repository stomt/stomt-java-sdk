package stomt4j.entities;

import com.google.gson.JsonObject;

public class Category {

	private String id;
	private String displayname;

	public Category(String id, String displayname) {
		this.id = id;
		this.displayname = displayname;
	}

	public Category(JsonObject category) {
		this.id = category.get("id").getAsString();
		this.displayname = category.get("displayname").getAsString();
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

	@Override
	public String toString() {
		return "Category [id=" + id + ", displayname=" + displayname + "]";
	}
}
