package stomt4j.entities;

import com.google.gson.JsonObject;

/**
 * @author Christoph Weidemeyer - c.weidemeyer at gmx.de
 */
public class Images {
	
	private Image avatar;
	private Image stomt;
	private Image profile;
	private Image cover;
	
	/**
	 * Constructor for images collection
	 * 
	 * @param images A images collection in json-format
	 */
	public Images(JsonObject images) {
		if (images.has("avatar")) {
			this.avatar = new Image(images.getAsJsonObject("avatar"));
			this.avatar.setContext(ImageContext.avatar.toString());
		}
		if (images.has("stomt")) {
			this.stomt = new Image(images.getAsJsonObject("stomt"));
			this.stomt.setContext(ImageContext.stomt.toString());
		}
		if (images.has("profile")) {
			this.profile = new Image(images.getAsJsonObject("profile"));
			this.profile.setContext(ImageContext.profile.toString());
		}
		if (images.has("cover")) {
			this.cover = new Image(images.getAsJsonObject("cover"));
			this.cover.setContext(ImageContext.cover.toString());
		}
	}
	
	/**
	 * @return The image object avatar
	 */
	public Image getAvatar() {
		return avatar;
	}

	/**
	 * @return The image object stomt
	 */
	public Image getStomt() {
		return stomt;
	}

	/**
	 * @return The image object profile
	 */
	public Image getProfile() {
		return profile;
	}
	
	/**
	 * @return The image object cover
	 */
	public Image getCover() {
		return cover;
	}
	
	/**
	 * A toString() method for the images collection - used for unit tests.
	 * 
	 * @return A condensed form of the image collection as String representation.
	 */
	@Override
	public String toString() {
		String avatarString;
		String stomtString;
		String profileString;
		
		if (this.avatar == null) {
			avatarString = "";
		} else {
			avatarString = this.avatar.toString();
		}
		if (this.stomt == null) {
			stomtString = "";
		} else {
			stomtString = this.stomt.toString();
		}
		if (this.profile == null) {
			profileString = "";
		} else {
			profileString = this.profile.toString();
		}
		
		return "Images [avatar=" + avatarString + ", stomt=" + stomtString + ", profile=" + profileString + "]";
	}
	
}
