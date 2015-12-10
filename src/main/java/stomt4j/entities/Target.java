package stomt4j.entities;

import com.google.gson.JsonObject;

/**
 * @author Christoph Weidemeyer - c.weidemeyer at gmx.de
 */
public class Target {
	
	/*
	 * Not implemented: ownedTargets, roles, widgets
	 */
	
	private String id;
	private String displayname;
	private Category category;
	private Images images;
	private boolean verified;
	private Stats stats;
	
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
		
		public Stats(JsonObject stats) {
			this.amountFollowers = stats.get("amountFollowers").getAsInt();
			this.amountFollows = stats.get("amountFollows").getAsInt();
			this.amountStomtsCreated = stats.get("amountStomtsCreated").getAsInt();
			this.amountStomtsReceived = stats.get("amountStomtsReceived").getAsInt();
		}
		
		@Override
		public String toString() {
			return "Stats [amountFollowers=" + amountFollowers + ", amountFollows=" + amountFollows + ", amountStomtsCreated=" + amountStomtsCreated + ", amountStomtsReceived=" + amountStomtsReceived + "]";
		}
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Images getImages() {
		return images;
	}

	public void setImages(Images images) {
		this.images = images;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public Stats getStats() {
		return stats;
	}

	public void setStats(Stats stats) {
		this.stats = stats;
	}
}
