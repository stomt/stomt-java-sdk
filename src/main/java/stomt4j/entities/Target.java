package stomt4j.entities;

import java.util.Arrays;

import com.google.gson.JsonObject;

public class Target {

	// Target = User
	/*
	 * id displayname category (categoryObject.toString()) images
	 * (imagesObject.toString()) ownedTargets (Array) roles (Array)
	 */

	// {"id":"test88576","displayname":"test88576","category":{"id":"users","displayname":"Users"},
	// "images":{"avatar":{"url":"https://test.rest.stomt.com/placeholders/30/5.png","w":30,"h":42}},
	// "ownedTargets":[],"roles":[]}

	public Target(JsonObject target) {
		this.id = target.get("id").getAsString();
		this.displayname = target.get("displayname").getAsString();
		this.category = new Category(target.getAsJsonObject("category"));
		// this.images = new Image[3]();
		// this.ownedTargets = new
		// this.roles
	}

	// user information
	private String id;
	private ImagesEntity images;
	private Target[] ownedTargets;
	private String email;
	private String name;
	private String lang;
	private InfluenceStatus influencestatus;
	private String roles;

	// additional target information

	private Category category;
	private boolean amIFollowing;
	private DetailsEntity details;
	private String type;
	private Stomt[] stomts;

	private StatsEntity stats;
	private String displayname;
	private String follows;
	private String follower;
	private int influencelevel;
	private String memberSince;

	public String getName() {
		if (name != null)
			return name;

		if (displayname != null)
			return displayname;

		if (details != null && details.name != null)
			return details.name;

		return null;
	}

	public String getLang() {
		return lang;
	}

	public String getId() {
		if (id != null)
			return id;

		if (details != null)
			return details.id;

		return null;
	}

	/**
	 * @return images's url or null if not available
	 */
	public String getAvatarSidebar() {
		if (images != null) {
			return images.avatar_sidebar.url;
		}

		return null;
	}

	/**
	 * @return images's url or null if not available
	 */
	public String getAvatarHeader() {
		if (images != null) {
			return images.avatar_header.url;
		}

		return null;
	}

	/**
	 * @return images's url or null if not available
	 */
	public String getAvatarTarget() {
		if (images != null && images.avatar_target != null && images.avatar_target.url != null)
			return images.avatar_target.url;

		return null;
	}

	/**
	 * @return images's url or null if not available
	 */
	public String getAvatarStomt() {
		if (images != null) {
			return images.avatar_stomt.url;
		}

		return null;
	}

	public String getType() {
		return type;
	}

	public int getAmountFollows() {
		if (stats != null)
			return stats.amountFollows;

		return -1;
	}

	public int getAmountFollowers() {
		if (stats != null)
			return stats.amountFollowers;

		return -1;
	}

	public int getAmountStomtsCreated() {
		if (stats != null)
			return stats.amountStomtsCreated;

		return -1;
	}

	public int getActualInfluenceLevel() {
		if (details != null && details.influencestatus != null)
			return details.influencestatus.actualInfluenceLevel;

		return -1;
	}

	public String getAvatarFollower() {
		if (images != null) {
			return images.avatar_follower.url;
		}

		return null;
	}

	public boolean getAmIFollowing() {
		return amIFollowing;
	}

	public void setAmIFollowing(boolean amIFollowing) {
		this.amIFollowing = amIFollowing;
	}

	public Stomt[] getStomts() {
		return stomts;
	}

	public int getAmountStomtsReceived() {
		if (stats != null)
			return stats.amountStomtsReceived;

		return -1;
	}

	// public void createStomt(Context context) {
	// Intent intent = new Intent(context, CreateStomtActivity.class);
	// intent.putExtra(CreateStomtActivity.KEY_TARGET, Utils.GSON.toJson(this));
	// context.startActivity(intent);
	// }

	private class ImagesEntity {
		private ImageEntity avatar_sidebar;
		private ImageEntity avatar_header;
		private ImageEntity avatar_stomt;
		private ImageEntity avatar_target;
		private ImageEntity avatar_follower;
		private ImageEntity avatar_comment;

		private class ImageEntity {
			private String url;
		}
	}

	private class StatsEntity {
		private int amountStomtsReceived;
		private int amountStomtsCreated;
		private int amountFollows;
		private int amountFollowers;
		private int amountStomts;
	}

	private class DetailsEntity {
		private String id;
		private String email;
		private String name;
		private ImagesEntity images;
		private String lang;
		private InfluenceStatus influencestatus;
	}

	private class InfluenceStatus {
		private int nextLevelPoints;
		private int nextLevel;
		private int actualInfluenceLevel;
		private int nextLevelPointsDifference;
		private int actualInfluencePoints;
		private int influenceLevelBarPercentage;
	}

	@Override
	public String toString() {
		return "Target [id=" + id + ", images=" + images + ", ownedTargets=" + Arrays.toString(ownedTargets)
				+ ", email=" + email + ", name=" + name + ", lang=" + lang + ", influencestatus=" + influencestatus
				+ ", roles=" + roles + ", category=" + category + ", amIFollowing=" + amIFollowing + ", details="
				+ details + ", type=" + type + ", stomts=" + Arrays.toString(stomts) + ", stats=" + stats
				+ ", displayname=" + displayname + ", follows=" + follows + ", follower=" + follower
				+ ", influencelevel=" + influencelevel + ", memberSince=" + memberSince + "]";
	}
}
