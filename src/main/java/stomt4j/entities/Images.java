package stomt4j.entities;

import com.google.gson.JsonObject;

/**
 * @author Christoph Weidemeyer - c.weidemeyer at gmx.de
 */
public class Images {
	
	private Image avatar;
	private Image stomt;
	private Image profile;
	
	public Images(JsonObject images) {
		if (images.has("avatar")) {
			this.avatar = new Image(images.getAsJsonObject("avatar"));
		}
		if (images.has("stomt")) {
			this.stomt = new Image(images.getAsJsonObject("stomt"));
		}
		if (images.has("profile")) {
			this.profile = new Image(images.getAsJsonObject("profile"));
		}
	}
	
	public Image getAvatar() {
		return avatar;
	}

	public Image getStomt() {
		return stomt;
	}

	public Image getProfile() {
		return profile;
	}
	
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
	
	public class Image {
		
//		private String context;
//		private String name;
//		private String data;
		private String url;
		private int width;
		private int height;
		
		public Image(JsonObject image) {
			if (image.has("url")) {
				this.url= image.get("url").getAsString();
			}
			if (image.has("w")) {
				this.width= image.get("w").getAsInt();
			}
			if (image.has("h")) {
				this.height= image.get("h").getAsInt();
			} else {
				
			}
		}

		public String getUrl() {
			return url;
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}
		
		@Override
		public String toString() {
			return "Image [url=" + url + ", width=" + width + ", height=" + height + "]";
		}
	}
}
