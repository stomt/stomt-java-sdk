package stomt4j.entities;

import com.google.gson.JsonObject;

/**
 * The entity Target.
 * 
 * @author Christoph Weidemeyer - c.weidemeyer at gmx.de
 */
public class Target {
	
// TODO: implement ownedTargets, roles, widgets
	
	private String id;
	private String displayname;
	private Category category;
	private Images images;
	private boolean verified;
	private Stats stats;
	
	/**
	 * Constructor for target-objects
	 * 
	 * @param target A target-object in json-format
	 */
	public Target(JsonObject target) {
		this.category = new Category(target.getAsJsonObject("category"));
		target.remove("category");
		this.id = target.get("id").getAsString();
		this.displayname = target.get("displayname").getAsString();
		this.images = new Images(target.getAsJsonObject("images"));
		this.verified = target.get("verified").getAsBoolean();
		if (target.has("stats")) {
			this.stats = new Stats(target.getAsJsonObject("stats"));
		}
	}
	
	/**
	 * @return The identifier of a target
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return The displayname of a target
	 */
	public String getDisplayname() {
		return displayname;
	}

	/**
	 * @return The category object of a target
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @return The images collection of a target
	 */
	public Images getImages() {
		return images;
	}

	/**
	 * @return true if the target is verified
	 */
	public boolean isVerified() {
		return verified;
	}

	/**
	 * @return The stats object of the target
	 */
	public Stats getStats() {
		return stats;
	}
	
	/**
	 * A toString() method for the entity Target - used for unit tests.
	 * 
	 * @return A String representation of the target entity
	 */
	@Override
	public String toString() {
		if (stats != null) {
			return "Target [id=" + id + ", displayname=" + displayname + ", category=" + category.toString() +  ", images=" + images.toString() +  ", verified=" + verified +  ", stats=" + stats.toString() + "]";
		}
		return "Target [id=" + id + ", displayname=" + displayname + ", category=" + category.toString() +  ", images=" + images.toString() +  ", verified=" + verified + "]";
	}
	
	private static class Stats {
		
		private int amountFollowers;
		private int amountFollows;
		private int amountStomtsCreated;
		private int amountStomtsReceived;
		
		/**
		 * Constructor for stats-objects
		 * 
		 * @param stats A stats-object in json-format
		 */
		public Stats(JsonObject stats) {
			this.amountFollowers = stats.get("amountFollowers").getAsInt();
			this.amountFollows = stats.get("amountFollows").getAsInt();
			this.amountStomtsCreated = stats.get("amountStomtsCreated").getAsInt();
			this.amountStomtsReceived = stats.get("amountStomtsReceived").getAsInt();
		}
		
		/**
		 * A toString() method for the entity stats - used for unit tests.
		 * 
		 * @return A String representation of the stats entity
		 */
		@Override
		public String toString() {
			return "Stats [amountFollowers=" + amountFollowers + ", amountFollows=" + amountFollows + ", amountStomtsCreated=" + amountStomtsCreated + ", amountStomtsReceived=" + amountStomtsReceived + "]";
		}
	}
}
