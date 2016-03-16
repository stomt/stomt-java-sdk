package stomt4j.entities;

import com.google.gson.JsonObject;

/**
 * The entity Category.
 * 
 * @author Christoph Weidemeyer - c.weidemeyer at gmx.de
 */
public class Category {

	private String id;
	private String displayname;
	
	/**
	 * Constructor for category-objects.
	 * 
	 * @param id The identifier of the category
	 * @param displayname The displayed name of the category
	 */
	public Category(String id, String displayname) {
		this.id = id;
		this.displayname = displayname;
	}

	/**
	 * Constructor for category-objects.
	 * 
	 * @param category The category object in json-format
	 */
	public Category(JsonObject category) {
		this.id = category.get("id").getAsString();
		this.displayname = category.get("displayname").getAsString();
	}

	/**
	 * @return The category identifier
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id The category identifier
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return The displayed name of the category
	 */
	public String getDisplayname() {
		return displayname;
	}

	/**
	 * @param displayname The displayed name of the category
	 */
	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	/**
	 * A toString() method for the entity Category - used for unit tests.
	 * 
	 * @return A String representation of the category entity
	 */
	@Override
	public String toString() {
		return "Category [id=" + id + ", displayname=" + displayname + "]";
	}
}
